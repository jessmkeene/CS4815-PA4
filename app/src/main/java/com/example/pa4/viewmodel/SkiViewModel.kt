//Chloe Polit and Jessica Keene

package com.example.pa4.viewmodel

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pa4.data.SettingsRepository
import com.example.pa4.data.SkiState
import com.example.pa4.data.WeatherRepository
import com.example.pa4.models.Coordinate
import com.example.pa4.models.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import wpics.weather.models.UnitSystem

class SkiViewModel(
    private val repository: LocationRepository,
    private val sensorManager: SensorManager,
    private val weatherRepository: WeatherRepository = WeatherRepository(), //default instance
    private val settingsRepository: SettingsRepository
) : ViewModel(), SensorEventListener {

    private val _uiState = MutableStateFlow(SkiState())
    val uiState: StateFlow<SkiState> = _uiState.asStateFlow()

    private val _currentUnits = MutableStateFlow(UnitSystem.IMPERIAL)

    private val accelerometer: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    init {
        observeLocation()
        observeSettings()
        accelerometer?.let { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // ── Sensor ────────────────────────────────────────────────────────────────

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type != Sensor.TYPE_ACCELEROMETER) return
        val (x, y, z) = event.values
        val magnitude = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
        val isStill = magnitude < STILL_THRESHOLD

        _uiState.update { current ->
            current.copy(
                isStill = isStill,
                confidence = calculateConfidence(current.isPressed, isStill)
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    // ── Location + Weather ────────────────────────────────────────────────────

    private fun observeLocation() {
        viewModelScope.launch {
            repository.locationUpdates.collect { coordinate ->
                val still = isUserStill(coordinate)
                _uiState.update { current ->
                    current.copy(
                        currentLocation = coordinate,
                        isStill = still,
                        confidence = calculateConfidence(current.isPressed, still)
                    )
                }
                fetchWeather(coordinate, _currentUnits.value)
            }
        }
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsRepository.settingsFlow.collect { settings ->
                _currentUnits.value = settings.unitSystem
                _uiState.value.currentLocation?.let { coord ->
                    fetchWeather(coord, settings.unitSystem)
                }
            }
        }
    }

    private fun fetchWeather(
        coordinate: Coordinate,
        units: UnitSystem = UnitSystem.IMPERIAL
        ) {
        viewModelScope.launch {
            val result = weatherRepository.getWeather(coordinate.lat, coordinate.lng, units)
            _uiState.update { current ->
                current.copy(
                    temperature = result.temperature,
                    weatherDesc = result.description,
                    windSpeed = result.windSpeed,
                    humidity = result.humidity,
                    locationName = result.locationName
                )
            }
        }
    }

    // ── UI Actions ────────────────────────────────────────────────────────────

    fun onRunButtonPressed() {
        _uiState.update { current ->
            current.copy(
                isPressed = !current.isPressed,
                confidence = calculateConfidence(!current.isPressed, current.isStill)
            )
        }
    }

    fun resetRun() {
        _uiState.value = SkiState()
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun isUserStill(newCoord: Coordinate): Boolean {
        val last = _uiState.value.currentLocation ?: return true
        return distanceBetween(last, newCoord) < 2.0f
    }

    private fun distanceBetween(a: Coordinate, b: Coordinate): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            a.lat, a.lng,
            b.lat, b.lng,
            results
        )
        return results[0]
    }

    private fun calculateConfidence(isPressed: Boolean, isStill: Boolean): Float {
        return when {
            isPressed && !isStill  -> 1.0f
            isPressed && isStill   -> 0.3f
            !isPressed && !isStill -> 0.6f
            else                   -> 0.0f
        }
    }

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

    // ── Factory ───────────────────────────────────────────────────────────────

    companion object {
        private const val STILL_THRESHOLD = 10.5f

        fun factory(
            repository: LocationRepository,
            sensorManager: SensorManager,
            settingsRepository: SettingsRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                SkiViewModel(
                    repository = repository,
                    sensorManager = sensorManager,
                    weatherRepository = WeatherRepository(),
                    settingsRepository = settingsRepository
                ) as T
        }
    }
}