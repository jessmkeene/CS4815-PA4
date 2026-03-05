package com.example.pa4.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import wpics.weather.models.UnitSystem

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsRepository(private val context: Context) {

    companion object {
        private val UNIT_SYSTEM = stringPreferencesKey("unit_system")
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val BACKGROUND_TRACKING = booleanPreferencesKey("background_tracking")
    }

    val settingsFlow: Flow<SettingsState> = context.dataStore.data.map { prefs ->
        SettingsState(
            unitSystem = UnitSystem.valueOf(
                prefs[UNIT_SYSTEM] ?: UnitSystem.IMPERIAL.name
            ),
            isDarkMode = prefs[DARK_MODE] ?: true,
            isBackgroundTrackingEnabled = prefs[BACKGROUND_TRACKING] ?: true
        )
    }

    suspend fun setUnitSystem(unit: UnitSystem) {
        context.dataStore.edit { it[UNIT_SYSTEM] = unit.name }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[DARK_MODE] = enabled }
    }

    suspend fun setBackgroundTracking(enabled: Boolean) {
        context.dataStore.edit { it[BACKGROUND_TRACKING] = enabled }
    }
}