package com.elevate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.elevate.R
import com.elevate.ui.theme.ElevateTheme

class AchievementsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElevateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AchievementsScreen()
                }
            }
        }
    }
}

@Composable
fun AchievementsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFFFF0F5))
                .padding(16.dp)
                .padding(bottom = 72.dp) // reserve space for bottom nav
        ) {
            MonthHeader()
            Spacer(modifier = Modifier.height(12.dp))
            ProductivitySection()
            Spacer(modifier = Modifier.height(16.dp))
            MissionsSection()
            Spacer(modifier = Modifier.height(24.dp))
            CompletedMissionsSection()
        }

        // Bottom navigation stays fixed at bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            BottomNavigationBar()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AchievementsScreenPreview() {
    ElevateTheme {
        AchievementsScreen()
    }
}

@Composable
fun MonthHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Month",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp, top = 22.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),  // Spacing between months
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Aug", "Sep", "Oct", "Nov", "Dec").forEach { month ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .wrapContentWidth()  // Let each month take the space it needs
                ) {
                    // Icon behind "Oct"
                    if (month == "Oct") {
                        Image(
                            painter = painterResource(id = R.drawable.pink_icon), // Use your icon resource
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp) // Adjust the size of the icon as needed
                                .align(Alignment.Center) // Center the icon behind the text
                        )
                    }

                    // Text
                    Text(
                        text = month,
                        fontSize = if (month == "Oct") 32.sp else 16.sp,
                        fontWeight = if (month == "Oct") FontWeight.Bold else FontWeight.Normal,
                        color = Color.Black,
                        maxLines = 1, // Ensure no line break
                        overflow = TextOverflow.Visible, // Allow text to be visible without truncation
                        modifier = Modifier
                            .align(Alignment.Center) // Ensure the text is centered within the Box
                            .zIndex(1f)  // Ensures text is above the icon
                    )
                }
            }
        }

        Text(
            text = "2025",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}




@Composable
fun ProductivitySection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD983BB), RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Collect the stars and let’s see how productive you are!",
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Left side
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_star_girl),
                        contentDescription = "Stars",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("My Stars", fontSize = 13.sp, color = Color.White)
                        Text("10", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                // Divider
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .width(1.dp)
                        .background(Color.White.copy(alpha = 0.5f))
                )

                // Right side
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        "Your productivity\nlevel is still low",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "You still have time,\nkeep up the spirit!",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

@Composable
fun MissionsSection() {
    Text(
        "October Missions",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
    MissionCard(title = "Complete 7 habits", stars = "1 star", buttonText = "Collect")
    MissionCard(title = "Make 15 days streak", stars = "2 star")
    MissionCard(title = "Make 30 day streak", stars = "3 star")
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text("More ▼", color = Color.Gray)
    }
}

@Composable
fun MissionCard(title: String, stars: String, buttonText: String? = null) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp), clip = false)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                colorFilter = ColorFilter.tint(Color(0xFFD983BB))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Text("Get $stars", fontSize = 12.sp, color = Color.Gray)
            }
            buttonText?.let {
                OutlinedButton(
                    onClick = { },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, Color(0xFFD983BB)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFFD983BB)
                    )
                ) {
                    Text(it, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun CompletedMissionsSection() {
    Text("Completed Missions", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    Spacer(modifier = Modifier.height(8.dp))
    MissionCard(title = "Make 7 days streak", stars = "1 star", buttonText = "Collected")
}

@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD983BB))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(iconRes = R.drawable.ic_home, label = "Home")
        BottomNavItem(iconRes = R.drawable.ic_achievements, label = "Achievements")
        BottomNavItem(iconRes = R.drawable.ic_progress, label = "Progress")
    }
}

@Composable
fun BottomNavItem(iconRes: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(25.dp) // Change 48.dp to your desired size
        )
        Text(label, fontSize = 16.sp, color = Color.White)
    }
}
