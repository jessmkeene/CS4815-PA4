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
fun PrivacyScreen() {
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
                text = "▲  YOUR DATA",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "PRIVACY",
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
                    .background(MaterialTheme.colorScheme.background)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "No data ever leaves your device.",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(Modifier.height(36.dp))


            PrivacySectionHeader("PERMISSIONS")
            Spacer(Modifier.height(12.dp))

            PrivacyCard(
                icon = Icons.Default.LocationOn,
                title = "Fine Location",
                permission = "ACCESS_FINE_LOCATION",
                reason = "Required to record precise GPS coordinates during your ski run. " +
                        "Used only while the app is active or a run is being recorded."
            )
            Spacer(Modifier.height(12.dp))

            PrivacyCard(
                icon = Icons.Default.LocationSearching,
                title = "Background Location",
                permission = "ACCESS_BACKGROUND_LOCATION",
                reason = "Allows GPS tracking to continue when the app is minimized. " +
                        "Only active when Background Tracking is enabled in Settings " +
                        "and you are within the resort geofence."
            )
            Spacer(Modifier.height(12.dp))

            PrivacyCard(
                icon = Icons.Default.DirectionsRun,
                title = "Activity Recognition",
                permission = "ACTIVITY_RECOGNITION",
                reason = "Allows the accelerometer to detect physical movement. " +
                        "Used to determine whether you are skiing or stationary, " +
                        "contributing to the confidence score."
            )
            Spacer(Modifier.height(12.dp))

            PrivacyCard(
                icon = Icons.Default.NotificationsActive,
                title = "Foreground Service",
                permission = "FOREGROUND_SERVICE_LOCATION",
                reason = "Required by Android to display a persistent notification " +
                        "when background location tracking is active. This ensures " +
                        "you always know when the app is recording."
            )

            Spacer(Modifier.height(24.dp))

            PrivacySectionHeader("DATA HANDLING")
            Spacer(Modifier.height(12.dp))

            DataHandlingCard(
                icon = Icons.Default.PhoneAndroid,
                title = "Stored On-Device Only",
                body = "All run data, GPS coordinates, and settings are stored " +
                        "locally on your device using Android DataStore. Nothing " +
                        "is transmitted to external servers."
            )
            Spacer(Modifier.height(12.dp))

            DataHandlingCard(
                icon = Icons.Default.Cloud,
                title = "Weather API",
                body = "Current weather conditions are fetched from OpenWeatherMap " +
                        "using your GPS coordinates. Only latitude and longitude are " +
                        "sent — no personal identifiers are included in the request."
            )
            Spacer(Modifier.height(12.dp))

            DataHandlingCard(
                icon = Icons.Default.Delete,
                title = "Data Deletion",
                body = "Uninstalling the app permanently removes all stored data " +
                        "from your device. No copies are retained anywhere else."
            )
        }
    }
}

@Composable
private fun PrivacySectionHeader(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f).height(1.dp).background(SlateLight))
        Text(
            text = "  $title  ",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )
        Box(modifier = Modifier.weight(1f).height(1.dp).background(SlateLight))
    }
}

@Composable
private fun PrivacyCard(
    icon: ImageVector,
    title: String,
    permission: String,
    reason: String
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
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(text = title, color = MaterialTheme.colorScheme.primary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Text(text = permission, color = MaterialTheme.colorScheme.primary, fontSize = 11.sp, letterSpacing = 1.sp)
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(text = reason, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp, lineHeight = 20.sp)
        }
    }
}

@Composable
private fun DataHandlingCard(
    icon: ImageVector,
    title: String,
    body: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(20.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(text = title, color = MaterialTheme.colorScheme.primary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(10.dp))
            Text(text = body, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp, lineHeight = 20.sp)
        }
    }
}