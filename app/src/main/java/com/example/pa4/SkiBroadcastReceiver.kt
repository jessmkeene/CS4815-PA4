package com.example.pa4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SkiBroadcastReceiver : BroadcastReceiver() {

    /**
     * This function is triggered when the user clicks "begin run"
     *
     * @param context An application context
     * @param intent An intent
     */
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                // Potential start of a sleep session
                showToast(context, "Power Connected: Sleep Tracking Optimized")
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                // User is likely awake or moving the device
                showToast(context, "Power Disconnected: Tracking Paused")
            }
        }
    }

    /**
     * A helper function to display a toast message.
     *
     * @param context An application context
     * @param message A message to be displayed.
     */
    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
