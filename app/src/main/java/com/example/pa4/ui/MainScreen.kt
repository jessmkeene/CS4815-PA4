package com.example.pa4.ui

import android.hardware.SensorManager
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pa4.data.SettingsRepository
import com.example.pa4.models.LocationRepository
import com.example.pa4.viewmodel.SkiViewModel
import com.example.pa4.ui.components.BottomNavBar

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    locationRepository: LocationRepository,
    sensorManager: SensorManager,
    settingsRepository: SettingsRepository
) {
    val navController = rememberNavController()
    val skiViewModel: SkiViewModel = viewModel(
        factory = SkiViewModel.factory(locationRepository, sensorManager)  // ← added sensorManager
    )

    Scaffold(
        modifier = modifier,
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "ski",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("ski")      { SkiScreen(vm = skiViewModel) }
            composable("context")  { ContextScreen() }
            composable("privacy")  { PrivacyScreen() }
            composable("settings") { SettingsScreen(settingsRepository = settingsRepository) }
        }
    }
}