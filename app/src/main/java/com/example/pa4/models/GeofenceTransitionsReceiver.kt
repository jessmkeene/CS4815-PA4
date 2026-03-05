package com.example.pa4.models

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pa4.services.ForegroundLocationService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceTransitionsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent) ?: return

        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
            return
        }

        when (geofencingEvent.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                context.startForegroundService(
                    Intent(context, ForegroundLocationService::class.java)
                )
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                context.stopService(
                    Intent(context, ForegroundLocationService::class.java)
                )
            }
        }
    }
}