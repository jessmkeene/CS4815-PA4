package com.example.pa4.models

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.coroutines.resume

class PlayServicesLocationRepository(
    private val context: Context
) : LocationRepository {

    private val fused by lazy { LocationServices.getFusedLocationProviderClient(context) }
    private val geofencing by lazy { LocationServices.getGeofencingClient(context) }

    // ↓ ADD THIS — continuous location flow for SkiViewModel and ForegroundLocationService
    @RequiresPermission(allOf = [
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ])
    override val locationUpdates: Flow<Coordinate> = callbackFlow {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 3000L  // every 3 seconds
        ).build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    trySend(Coordinate(location.latitude, location.longitude))
                }
            }
        }

        fused.requestLocationUpdates(request, callback, Looper.getMainLooper())

        awaitClose { fused.removeLocationUpdates(callback) }
    }

    // ... rest of your existing functions unchanged
    @RequiresPermission(allOf = [
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ])
    override suspend fun getLastKnownOrCurrentLocation(): Coordinate? =
        suspendCancellableCoroutine { continuation ->
            fused.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    continuation.resume(Coordinate(location.latitude, location.longitude))
                } else {
                    val cts = CancellationTokenSource()
                    fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                        .addOnSuccessListener { cur ->
                            continuation.resume(cur?.let { Coordinate(it.latitude, it.longitude) })
                        }
                }
            }
        }

    override suspend fun reverseGeocodeCity(coordinate: Coordinate): String? =
        withContext(Dispatchers.IO) {
            try {
                val g = Geocoder(context, Locale.getDefault())
                val r = g.getFromLocation(coordinate.lat, coordinate.lng, 1)
                r?.firstOrNull()?.locality ?: r?.firstOrNull()?.subAdminArea
            } catch (e: Exception) {
                null
            }
        }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override suspend fun addGeofence(
        id: String,
        center: Coordinate,
        radiusMeters: Float
    ): Result<Unit> =
        try {
            val geofence = Geofence.Builder()
                .setRequestId(id)
                .setCircularRegion(center.lat, center.lng, radiusMeters)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build()

            val req = GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build()

            Tasks.await(geofencing.addGeofences(req, geofencePendingIntent()))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun removeGeofence(id: String): Result<Unit> =
        try {
            Tasks.await(geofencing.removeGeofences(listOf(id)))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    private fun geofencePendingIntent(): PendingIntent {
        val i = Intent(context, GeofenceTransitionsReceiver::class.java)
        return PendingIntent.getBroadcast(
            context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}