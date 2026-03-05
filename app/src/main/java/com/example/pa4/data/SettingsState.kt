package com.example.pa4.data

import wpics.weather.models.UnitSystem

data class SettingsState(
    val unitSystem: UnitSystem = UnitSystem.IMPERIAL,
    val isDarkMode: Boolean = true,
    val isBackgroundTrackingEnabled: Boolean = true
)