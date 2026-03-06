//Chloe Polit and Jessica Keene

package com.example.pa4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pa4.data.SettingsRepository
import com.example.pa4.data.SettingsState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import wpics.weather.models.UnitSystem

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    val settingsState: StateFlow<SettingsState> = repository.settingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsState()
        )

    fun setUnitSystem(unit: UnitSystem) {
        viewModelScope.launch { repository.setUnitSystem(unit) }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch { repository.setDarkMode(enabled) }
    }

    fun setBackgroundTracking(enabled: Boolean) {
        viewModelScope.launch { repository.setBackgroundTracking(enabled) }
    }

    companion object {
        fun factory(repository: SettingsRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    SettingsViewModel(repository) as T
            }
    }
}