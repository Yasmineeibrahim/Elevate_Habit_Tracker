package com.elevate.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

@Composable
fun WaveBackground() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        val width = size.width
        val height = size.height

        val wavePath = Path().apply {
            moveTo(0f, height * 0.6f)
            quadraticBezierTo(
                width * 0.25f, height * 1.1f,
                width * 0.5f, height * 0.7f
            )
            quadraticBezierTo(
                width * 0.75f, height * 0.3f,
                width, height * 0.75f
            )
            lineTo(width, 0f)
            lineTo(0f, 0f)
            close()
        }

        drawPath(
            path = wavePath,
            color = Color(0xFFFFA4B2) // اللون الوردي الفاتح
        )
    }
}
