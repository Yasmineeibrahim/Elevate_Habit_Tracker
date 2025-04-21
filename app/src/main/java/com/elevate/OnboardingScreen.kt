package com.elevate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.elevate.ui.theme.*

class OnboardingScreen: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme{
                OnboardingScreenContent(this)
            }
        }
    }
}

@Composable
fun OnboardingScreenContent(activity: ComponentActivity? = null){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.onboarding_image),
                contentDescription = "Onboarding Image",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Make Time for a Healthier You .",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = Poppins,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "\"Success starts with smart time management !\nPlan your workouts, meals , and self-care routines to stay on track with your build consistent habits by organizing your schedule , setting reminders , and keeping you accountable . Priortize your well-being and achieve a balanced , healthier lifestyle ! \" ",
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal
                    )
                )

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.height(32.dp)) // Adjust this value as needed

                IconButton(
                    onClick = {  activity?.let {
                        val intent = Intent(it, SleepActivity::class.java)
                        it.startActivity(intent)
                    } },
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

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingScreenPreview() {
    MaterialTheme {
        OnboardingScreenContent()
    }
}
