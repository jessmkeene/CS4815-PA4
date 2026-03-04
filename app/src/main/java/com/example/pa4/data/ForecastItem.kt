package com.example.pa4.data
import wpics.weather.models.MainData
import wpics.weather.models.WeatherDesc
import wpics.weather.models.WindData
import wpics.weather.models.Rain

data class ForecastItem(
    val dt: Long? = null,
    val main: MainData? = null,
    val weather: List<WeatherDesc>? = null,
    val wind: WindData? = null,
    val rain: Rain? = null
)