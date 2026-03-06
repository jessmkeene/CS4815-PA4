//Chloe Polit and Jessica Keene
package com.example.pa4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pa4.ui.MainScreen
import com.example.pa4.ui.theme.PA4Theme
import com.example.pa4.models.PlayServicesLocationRepository
import android.hardware.SensorManager
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import androidx.compose.runtime.collectAsState
import com.example.pa4.data.SettingsRepository
import com.example.pa4.data.SettingsState
import androidx.compose.runtime.getValue


class MainActivity : ComponentActivity() {private val requestPermissions = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) { permissions ->
    val fineLocation = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
    val coarseLocation = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    val activityRecognition = permissions[Manifest.permission.ACTIVITY_RECOGNITION] == true

    if (fineLocation || coarseLocation) {
        android.util.Log.d("MainActivity", "Location permission granted")
    }
    else {
        android.util.Log.w("MainActivity", "Location permission denied — weather and location tracking unavailable")
        android.widget.Toast.makeText(
            this,
            "Location permission is required for weather and run tracking.",
            android.widget.Toast.LENGTH_LONG
        ).show()
    }

    if (!activityRecognition) {
        android.util.Log.w("MainActivity", "Activity recognition denied — motion detection unavailable")
        android.widget.Toast.makeText(
            this,
            "Activity recognition is needed to detect when you're skiing.",
            android.widget.Toast.LENGTH_LONG
        ).show()
    }
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locationRepository = PlayServicesLocationRepository(this)
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val settingsRepository = SettingsRepository(this)

        requestPermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        )

        enableEdgeToEdge()
        setContent {
            val settingsState by settingsRepository.settingsFlow
                .collectAsState(initial = SettingsState())

            PA4Theme(darkTheme = settingsState.isDarkMode) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        locationRepository = locationRepository,
                        sensorManager = sensorManager,
                        settingsRepository = settingsRepository
                    )
                }
            }
        }
    }
}