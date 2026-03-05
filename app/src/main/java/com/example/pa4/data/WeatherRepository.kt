package com.example.pa4.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import wpics.weather.models.UnitSystem

class WeatherRepository {

    private val api: WeatherAPI = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    companion object {
        private const val API_KEY = "YOUR_API_KEY_HERE"
    }

    suspend fun getWeather(
        lat: Double,
        lon: Double,
        units: UnitSystem = UnitSystem.IMPERIAL
    ): WeatherResult {
        return try {
            val response = api.getCurrent(lat, lon, API_KEY, units.requestValue)
            WeatherResult(
                temperature = "${response.main?.temp?.toInt() ?: "--"}°",
                description = response.weather?.firstOrNull()?.description
                    ?.replaceFirstChar { it.uppercase() } ?: "Unknown",
                windSpeed = "${response.wind?.speed ?: "--"} mph",
                humidity = "${response.main?.humidity ?: "--"}%",
                locationName = response.name ?: "UNKNOWN MOUNTAIN"
            )
        } catch (e: Exception) {
            WeatherResult.error()
        }
    }
}

data class WeatherResult(
    val temperature: String,
    val description: String,
    val windSpeed: String,
    val humidity: String,
    val locationName: String
) {
    companion object {
        fun error() = WeatherResult(
            temperature = "--°",
            description = "Unavailable",
            windSpeed = "--",
            humidity = "--%",
            locationName = "UNKNOWN MOUNTAIN"
        )
    }
}