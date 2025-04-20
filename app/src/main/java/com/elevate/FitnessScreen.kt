package com.elevate.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.habittracker.R // ← غيّره لو اسم الباكدج بتاعك مختلف

@Composable
fun FitnessScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.image), // ← تأكد إن الصورة موجودة في res/drawable
            contentDescription = "Workout Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Push Your Limits,\nReach Your Goals.",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Turn your fitness dreams into reality! Set clear exercise goals, track your progress, and stay motivated every step of the way. Whether you're aiming for strength, endurance, or overall wellness, consistent effort leads to lasting results. Keep moving forward and achieve your best self.",
            fontSize = 20.sp,
            color = Color.Gray,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.End)
                .clip(CircleShape)
                .background(Color(0xFFA24D6A)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next",
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FitnessScreenPreview() {
    FitnessScreen()
}
