package com.elevate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elevate.ui.theme.Poppins
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreen1 : ComponentActivity() {

    override fun onStart() {
        super.onStart()
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            // المستخدم مسجل دخول بالفعل
            val intent = Intent(this, AchievementsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                OnboardingScreen(this)
            }
        }
    }
}

@Composable
fun OnboardingScreen(activity: ComponentActivity? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(2f))

            GradientTitle()

            Text(
                text = "Elevate every day.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp),
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
            )

            Spacer(modifier = Modifier.weight(2f))

            GradientButton {
                activity?.let {
                    it.startActivity(Intent(it, SplashScreen2::class.java))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun GradientTitle() {
    val gradientColors = listOf(Color(0xFFA7379E), Color(0xFFBD99A2))
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    brush = Brush.linearGradient(gradientColors),
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    fontSize = 50.sp

                )
            ) {
                append("E")
            }
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                )
            ) {
                append("levate")
            }
        },
        textAlign = TextAlign.Center
    )
}

@Composable
fun GradientButton(onClick: () -> Unit) {
    val gradientColors = listOf(Color(0xFFA7379E), Color(0xFFE7D8DE))


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(50))
            .background(Brush.horizontalGradient(gradientColors))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Get Started",
            color = Color.Black,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingPreview() {
   MaterialTheme {
        OnboardingScreen()
    }
}
