package com.example.pa4.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.WaterDrop
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
import com.example.pa4.data.SkiState
import com.example.pa4.ui.theme.*
import com.example.pa4.ui.components.StartRunButton
import com.example.pa4.viewmodel.SkiViewModel

/**
 * Composable function for rendering SkiScreen
 */
@Composable
fun SkiScreen(vm: SkiViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    val state by vm.uiState.collectAsState()

    SkiScreenContent(
        state = state,
        onStartRun = {vm.onRunButtonPressed()}
    )
    }

/**
 * Displays content of the screen
 * @param state Current ski state
 * @param onStartRun A Callback function for handling the ski button
 */
@Composable
fun SkiScreenContent(
    state: SkiState,
    onStartRun: () -> Unit
) {
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

                //location
                Text(
                    text = "▲  ${state.locationName}",
                    color = IceBlue,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "SKI TRACKER",
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
                        .background(
                            Brush.horizontalGradient(listOf(IceBlue, BrightBlue))
                        )
                )

                Spacer(Modifier.height(48.dp))

                StartRunButton(
                    isRunActive = state.isPressed,
                    onToggle = onStartRun
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = if (state.isPressed) "Run in progress…" else "Tap to begin your run",
                    color = OffWhite,
                    fontSize = 13.sp,
                    letterSpacing = 1.sp
                )

                Spacer(Modifier.height(48.dp))

                //weather
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(SlateLight)
                    )
                    Text(
                        text = "  CONDITIONS  ",
                        color = IceBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 3.sp
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(SlateLight)
                    )
                }

                Spacer(Modifier.height(20.dp))

                //temp card from weather app
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(SlateCard)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.temperature,
                            fontSize = 64.sp,
                            fontWeight = FontWeight.Black,
                            color = White,
                            letterSpacing = (-2).sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = state.weatherDesc.uppercase(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 3.sp,
                            color = IceBlue
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                //wind and humidity cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    WeatherStatCard(
                        icon = Icons.Default.Air,
                        label = "WIND",
                        value = state.windSpeed,
                        modifier = Modifier.weight(1f)
                    )
                    WeatherStatCard(
                        icon = Icons.Default.WaterDrop,
                        label = "HUMIDITY",
                        value = state.humidity,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(12.dp))
            }
        }
    }


//stat card from weather
@Composable
private fun WeatherStatCard(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SlateCard)
            .padding(vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = IceBlue,
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = value,
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = label,
                color = OffWhite,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}