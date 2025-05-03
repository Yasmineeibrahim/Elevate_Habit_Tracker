package com.elevate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elevate.components.BottomNavigationBar
import com.elevate.ui.theme.Poppins

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF0F5))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(bottom = 72.dp)
        ) {
            // Welcome Section
            WelcomeSection()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Today's Progress
            ProgressSection()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Active Habits
            ActiveHabitsSection()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Daily Motivation
            MotivationSection()
        }

        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            BottomNavigationBar(
                currentScreen = "home",
                onNavigate = { /* Handle navigation if needed */ }
            )
        }
    }
}

@Composable
private fun WelcomeSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Welcome back!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins
            )
            Text(
                text = "Let's make today amazing",
                fontSize = 16.sp,
                color = Color.Gray,
                fontFamily = Poppins
            )
        }
    }
}

@Composable
private fun ProgressSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD983BB)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Today's Progress",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = Poppins
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProgressItem("3/5", "Habits")
                ProgressItem("2h", "Focus")
                ProgressItem("85%", "Energy")
            }
        }
    }
}

@Composable
private fun ProgressItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = Poppins
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.8f),
            fontFamily = Poppins
        )
    }
}

@Composable
private fun ActiveHabitsSection() {
    Column {
        Text(
            text = "Active Habits",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        HabitCard("Morning Exercise", "6:00 AM", "30 min")
        Spacer(modifier = Modifier.height(12.dp))
        HabitCard("Reading", "8:00 PM", "20 min")
        Spacer(modifier = Modifier.height(12.dp))
        HabitCard("Meditation", "9:00 PM", "15 min")
    }
}

@Composable
private fun HabitCard(title: String, time: String, duration: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFD983BB))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Poppins
                )
                Text(
                    text = "$time • $duration",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontFamily = Poppins
                )
            }
        }
    }
}

@Composable
private fun MotivationSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF9F7AEA)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Daily Motivation",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = Poppins
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "\"The only way to do great work is to love what you do.\"",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.9f),
                fontFamily = Poppins
            )
            Text(
                text = "- Steve Jobs",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f),
                fontFamily = Poppins,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
} 