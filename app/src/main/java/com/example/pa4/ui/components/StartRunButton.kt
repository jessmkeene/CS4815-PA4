package com.example.pa4.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.pa4.viewmodel.SkiViewModel

@Composable
fun StartRunButton(
    isRunActive: Boolean,
    onToggle: () -> Unit
) {
    Button(
        onClick = onToggle,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isRunActive)
                MaterialTheme.colorScheme.error
            else
                MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = if (isRunActive) "⏹  END RUN" else "▶  START RUN",
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )
    }
}