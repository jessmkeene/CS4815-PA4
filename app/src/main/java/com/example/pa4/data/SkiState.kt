package com.example.pa4.data

data class SkiState(
    val buttonPressed: Boolean = false,
    val isStill: Boolean = false,
    val confidence: Int = 0
)