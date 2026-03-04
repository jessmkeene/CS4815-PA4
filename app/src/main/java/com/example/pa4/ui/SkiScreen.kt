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
import com.example.pa4.ui.theme.*
import com.example.pa4.ui.components.StartRunButton


@Composable
fun SkiScreen(
    //need to be connected to viewmodel
    onStartRun: () -> Unit = {},
    temperature: String = "27°F",
    weatherDesc: String = "Light Snow",
    windSpeed: String = "12 mph",
    humidity: String = "74%",
    locationName: String = "Mt. Wachusett",
    isRunActive: Boolean = false
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
                text = "▲  $locationName",
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
                isRunActive = isRunActive,
                onToggle = onStartRun
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = if (isRunActive) "Run in progress…" else "Tap to begin your run",
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
                        text = temperature,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Black,
                        color = White,
                        letterSpacing = (-2).sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = weatherDesc.uppercase(),
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
                    value = windSpeed,
                    modifier = Modifier.weight(1f)
                )
                WeatherStatCard(
                    icon = Icons.Default.WaterDrop,
                    label = "HUMIDITY",
                    value = humidity,
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