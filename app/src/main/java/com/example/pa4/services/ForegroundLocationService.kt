package com.example.pa4.services

import android.app.*
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.pa4.models.PlayServicesLocationRepository
import kotlinx.coroutines.*

class ForegroundLocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var repository: PlayServicesLocationRepository

    override fun onCreate() {
        super.onCreate()
        repository = PlayServicesLocationRepository(this)
        startForeground(1, buildNotification())
        collectLocation()
    }

    private fun collectLocation() {
        serviceScope.launch {
            repository.locationUpdates.collect {
                // repository flow keeps emitting — SkiViewModel collects separately
            }
        }
    }

    private fun buildNotification(): Notification {
        val channelId = "ski_tracking"
        val channel = NotificationChannel(
            channelId, "Ski Tracking", NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Ski Tracker Active")
            .setContentText("Recording your run in the background")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}