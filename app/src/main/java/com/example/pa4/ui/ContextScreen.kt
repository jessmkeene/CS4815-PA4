//Chloe Polit and Jessica Keene

package com.example.pa4.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun ContextScreen() {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .padding(top = 56.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "▲  HOW IT WORKS",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "CONTEXT",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 6.sp
            )
            Spacer(Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            )
            Spacer(Modifier.height(36.dp))

            ContextCard(
                icon = Icons.Default.TouchApp,
                title = "User Input Signal",
                body = "When you tap the Start Run button, the app registers your intent " +
                        "to begin a ski run. This is the strongest signal in the system — " +
                        "a deliberate press immediately raises your confidence score."
            )
            Spacer(Modifier.height(16.dp))

            ContextCard(
                icon = Icons.Default.Speed,
                title = "Motion Detection",
                body = "Your phone's accelerometer continuously measures movement. " +
                        "The app calculates the magnitude of acceleration across all three " +
                        "axes (X, Y, Z). If the magnitude exceeds the stillness threshold, " +
                        "the app detects you are moving and updates isStill accordingly."
            )
            Spacer(Modifier.height(16.dp))

            ContextCard(
                icon = Icons.Default.LocationOn,
                title = "GPS Location Tracking",
                body = "GPS coordinates are collected every 3 seconds via Google Play's " +
                        "Fused Location Provider. The distance between consecutive coordinates " +
                        "is used as a secondary movement check, confirming whether you are " +
                        "actively skiing or standing still at the lift."
            )
            Spacer(Modifier.height(16.dp))

            ContextCard(
                icon = Icons.Default.Analytics,
                title = "Confidence Score",
                body = "The three signals are fused into a single confidence score between " +
                        "0.0 and 1.0. A score of 1.0 means you pressed the button and are " +
                        "actively moving — a confirmed run. Lower scores handle edge cases " +
                        "like accidental presses (0.3) or movement without a press (0.6)."
            )
            Spacer(Modifier.height(16.dp))

            ConfidenceTable()
            Spacer(Modifier.height(16.dp))

            ContextCard(
                icon = Icons.Default.FmdGood,
                title = "Geofence Auto-Start",
                body = "A geofence is registered around the ski resort boundary. When your " +
                        "device enters this zone, the app automatically starts background " +
                        "location tracking via a Foreground Service. Exiting the resort " +
                        "boundary stops tracking to preserve battery life."
            )
        }
    }
}

@Composable
private fun ContextCard(
    icon: ImageVector,
    title: String,
    body: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(20.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = IceBlue,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = body,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 13.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun ConfidenceTable() {
    val rows = listOf(
        Triple("Pressed + Moving",    "1.0",  "Confirmed run"),
        Triple("Pressed + Still",     "0.3",  "Accidental press?"),
        Triple("Not Pressed + Moving","0.6",  "Likely skiing"),
        Triple("Not Pressed + Still", "0.0",  "Not on a run")
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = "Confidence Table",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(12.dp))
            rows.forEach { (state, score, label) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = state, color = White, fontSize = 13.sp)
                        Text(text = label, color = OffWhite, fontSize = 11.sp)
                    }
                    Text(
                        text = score,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                if (state != rows.last().first) {
                    HorizontalDivider(color = SlateLight, thickness = 0.5.dp)
                }
            }
        }
    }
}