package com.elevate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SleepActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SleepScreen()
            }
        }
    }
}

@Composable
fun SleepScreen(onNextClicked: () -> Unit = {}) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.drinkk),
                contentDescription = "Drink Illustration",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Improve your sleep quality",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "Improve your sleep quality by creating a relaxing bedtime routine, staying consistent with your schedule, and avoiding screens before bed.\nWake up feeling refreshed and ready for the day.",
                fontSize = 17.sp,
                color = Color.DarkGray
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(26.dp))

            IconButton(
                onClick = {
                    val intent = Intent(context, TakeoffActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE39EBF))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = "Next",
                    tint = Color.Black
                )
            }
        }
    }
}
