package com.elevate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.elevate.ui.theme.ElevateTheme
import com.elevate.ui.theme.Poppins
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.utils.noRippleClickable
import java.util.Calendar


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
    var selectedIndex by remember { mutableStateOf(1) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFFFF0F5))
                .padding(16.dp)
                .padding(bottom = 72.dp)
        ) {
            MonthHeader()
            Spacer(modifier = Modifier.height(12.dp))
            ProductivitySection()
            Spacer(modifier = Modifier.height(16.dp))
            MissionsSection()
            Spacer(modifier = Modifier.height(24.dp))
            CompletedMissionsSection()
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            AnimatedNavigationBar(
                selectedIndex = selectedIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(Color(0xFFD983BB), RoundedCornerShape(36.dp))
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(36.dp),
                        spotColor = Color(0xFFD983BB).copy(alpha = 0.5f)
                    ),
                ballColor = Color.White,
                barColor = Color(0xFFD983BB)
            ) {
                BottomNavItem(
                    selected = selectedIndex == 0,
                    iconId = R.drawable.ic_home,
                    label = "Home"
                ) { selectedIndex = 0 }

                BottomNavItem(
                    selected = selectedIndex == 1,
                    iconId = R.drawable.ic_achievements,
                    label = "Achievements"
                ) { selectedIndex = 1 }

                BottomNavItem(
                    selected = selectedIndex == 2,
                    iconId = R.drawable.person_icon,
                    label = "Profile"
                ) { selectedIndex = 2 }
            }
        }
    }
}

@Composable
fun BottomNavItem(selected: Boolean, iconId: Int, label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(if (selected) Color.White else Color.White.copy(alpha = 0.7f))
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = if (selected) Color.White else Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun MonthHeader() {
    // Months in order from January (0) to December (11)
    val months =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH)
    val currentMonth = months[currentMonthIndex]
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Month",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp, top = 22.dp),
            fontFamily = Poppins

        )

        // Pink icon with current month centered above
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.pink_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .align(Alignment.Center)
            )
            Text(
                text = currentMonth,
                fontSize = 32.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(1f)
            )
        }

        Text(
            text = currentYear.toString(),
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold
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
                text = "Collect the stars and let's see how productive you are!",
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp),
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_star_girl),
                        contentDescription = "Stars",
                        modifier = Modifier.size(100.dp)
                    )
                    Column {
                        Text(
                            "My Stars ",
                            fontSize = 15.sp,
                            color = Color.White,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "10",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = Poppins
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .width(1.dp)
                        .background(Color.White.copy(alpha = 0.5f))
                )

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
                        color = Color.White.copy(alpha = 0.8f),
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun MissionsSection() {
    val months =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH)
    val currentMonth = months[currentMonthIndex]
    Text(
        "${currentMonth} Missions",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        fontFamily = Poppins
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

@Preview(showBackground = true)
@Composable
fun AchievementsScreenPreview() {
    ElevateTheme {
        AchievementsScreen()
    }
}
