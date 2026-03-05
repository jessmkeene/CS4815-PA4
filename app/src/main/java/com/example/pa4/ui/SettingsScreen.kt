package com.example.pa4.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pa4.data.SettingsRepository
import com.example.pa4.ui.theme.*
import com.example.pa4.viewmodel.SettingsViewModel
import wpics.weather.models.UnitSystem

@Composable
fun SettingsScreen(
    settingsRepository: SettingsRepository,
    vm: SettingsViewModel = viewModel(factory = SettingsViewModel.factory(settingsRepository))
) {
    val state by vm.settingsState.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(NavyDeep, NavyMid, Color(0xFF0F172A))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .padding(top = 56.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Header ────────────────────────────────────────────────────────
            Text(
                text = "SETTINGS",
                color = White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 6.sp
            )
            Spacer(Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(2.dp)
                    .background(Brush.horizontalGradient(listOf(IceBlue, BrightBlue)))
            )
            Spacer(Modifier.height(36.dp))

            // ── Units ─────────────────────────────────────────────────────────
            SettingsSectionHeader("UNITS")
            Spacer(Modifier.height(12.dp))
            SettingsCard {
                Column {
                    listOf(UnitSystem.IMPERIAL, UnitSystem.METRIC).forEach { unit ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = unit.displayName,
                                color = White,
                                fontSize = 15.sp
                            )
                            RadioButton(
                                selected = state.unitSystem == unit,
                                onClick = { vm.setUnitSystem(unit) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = IceBlue,
                                    unselectedColor = SlateLight
                                )
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Display ───────────────────────────────────────────────────────
            SettingsSectionHeader("DISPLAY")
            Spacer(Modifier.height(12.dp))
            SettingsCard {
                SettingsToggleRow(
                    icon = Icons.Default.DarkMode,
                    label = "Dark Mode",
                    description = "Use dark theme throughout the app",
                    checked = state.isDarkMode,
                    onCheckedChange = { vm.setDarkMode(it) }
                )
            }

            Spacer(Modifier.height(24.dp))

            // ── Tracking ──────────────────────────────────────────────────────
            SettingsSectionHeader("TRACKING")
            Spacer(Modifier.height(12.dp))
            SettingsCard {
                SettingsToggleRow(
                    icon = Icons.Default.LocationOn,
                    label = "Background Tracking",
                    description = "Continue recording when app is closed",
                    checked = state.isBackgroundTrackingEnabled,
                    onCheckedChange = { vm.setBackgroundTracking(it) }
                )
            }
        }
    }
}

// ── Reusable components ───────────────────────────────────────────────────────

@Composable
private fun SettingsSectionHeader(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f).height(1.dp).background(SlateLight))
        Text(
            text = "  $title  ",
            color = IceBlue,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )
        Box(modifier = Modifier.weight(1f).height(1.dp).background(SlateLight))
    }
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SlateCard)
            .padding(20.dp)
    ) {
        content()
    }
}

@Composable
private fun SettingsToggleRow(
    icon: ImageVector,
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = IceBlue,
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(text = label, color = White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = description, color = OffWhite, fontSize = 12.sp)
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = White,
                checkedTrackColor = IceBlue,
                uncheckedThumbColor = OffWhite,
                uncheckedTrackColor = SlateLight
            )
        )
    }
}