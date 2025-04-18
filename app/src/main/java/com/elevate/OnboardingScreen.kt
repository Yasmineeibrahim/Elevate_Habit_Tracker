package com.elevate

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elevate.ui.theme.*
@Composable
fun OnboardingScreen(onNextClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftPink)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // تقدرِ تضيفي الصورة هنا لاحقًا
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.Transparent)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Make Time for a Healthier You.",
                fontSize = 24.sp,
                color = TextColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Success starts with smart time management! Plan your workouts, meals, and self-care routines...",
                fontSize = 16.sp,
                color = SubtitleColor
            )
        }

        Button(
            onClick = { onNextClick() },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = DarkPink),
            modifier = Modifier.size(64.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Next",
                tint = Color.White
            )
        }
    }
}
