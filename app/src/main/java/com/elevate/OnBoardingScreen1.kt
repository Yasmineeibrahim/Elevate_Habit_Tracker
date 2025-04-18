package com.elevate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elevate.ui.theme.Poppins

class OnboardingScreen1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                OnboardingContent1()
            }
        }
    }
}

@Composable
fun OnboardingContent1() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.goals_image),
                contentDescription = "Goals Illustration",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            // Text Block
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Track your goals to be a\nbetter version of yourself.",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = Poppins,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Stay motivated and achieve your goals with ease! Our app helps you track your progress, stay organized, and push yourself to be the best version of you. Set personal milestones, monitor your achievements, and stay inspired every step of the way. Your journey to self-improvement starts here!",
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal
                    )
                )

            }

            // Play Button
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.height(26.dp)) // Adjust this value as needed

                IconButton(
                    onClick = { /* TODO: Navigate forward */ },
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
fun OnboardingContent1Preview() {
    MaterialTheme {
        OnboardingContent1()
    }
}
