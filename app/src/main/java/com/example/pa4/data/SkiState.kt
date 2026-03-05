package com.example.pa4.data

import com.example.pa4.models.Coordinate

data class SkiState(
    val isPressed: Boolean = false,
    val isStill: Boolean = true,
    val confidence: Float = 0f,
    val currentLocation: Coordinate? = null,
    val locationName: String = "UNKNOWN MOUNTAIN",
    val temperature: String = "--°",
    val weatherDesc: String = "Loading...",
    val windSpeed: String = "--",
    val humidity: String = "--%"
)