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
import com.example.pa4.data.SettingsRepository

class MainActivity : ComponentActivity() {

    private fun requestPermissions() {
        val launcher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            // optional: handle permission results here
        }
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locationRepository = PlayServicesLocationRepository(this)
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val settingsRepository = SettingsRepository(this)

        requestPermissions()  // ← handle before setContent in production

        enableEdgeToEdge()
        setContent {
            PA4Theme {
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