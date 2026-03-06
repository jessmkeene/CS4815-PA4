//Chloe Polit and Jessica Keene

package com.example.pa4.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = IceBlue,
    secondary = NavyMid,
    tertiary = SlateCard
)

private val LightColorScheme = lightColorScheme(
    primary = IceBlue,
    secondary = NavyMid,
    tertiary = SlateLight
)

@Composable
fun PA4Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}