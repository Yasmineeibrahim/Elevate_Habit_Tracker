package com.elevate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elevate.ui.theme.Poppins

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
fun FitnessScreen(onNextClicked: () -> Unit = {}) {
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
                painter = painterResource(id = R.drawable.image),
                contentDescription = "Drink Illustration",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth

            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "Push Your Limits,Reach Your Goals.",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = Poppins,
            )

            Spacer(modifier = Modifier.height(12.dp))
            Column (
                modifier = Modifier.fillMaxWidth()
            ){ Text(
                text = "Turn your fitness dreams into reality! Set clear exercise goals, track your progress, and stay motivated every step of the way. Whether you're aiming for strength, endurance, or overall wellness, consistent effort leads to lasting results. Keep moving forward and achieve your best self.",
                textAlign = TextAlign.Start,
                style = TextStyle(fontSize = 16.sp,
                    color = Color.DarkGray,
                    fontFamily = Poppins,

                    fontWeight = FontWeight.Normal
                )
            )
            }  }


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