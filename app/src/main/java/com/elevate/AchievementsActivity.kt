package com.elevate

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.elevate.components.BottomNavigationBar
import com.elevate.ui.theme.ElevateTheme
import com.elevate.ui.theme.Poppins
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

// SharedPreferences helpers
fun saveCompletedMissions(context: Context, missions: Set<String>) {
    context.getSharedPreferences("achievements", Context.MODE_PRIVATE)
        .edit()
        .putStringSet("completed_missions", missions)
        .apply()
}

fun loadCompletedMissions(context: Context): MutableSet<String> {
    val prefs = context.getSharedPreferences("achievements", Context.MODE_PRIVATE)
    return prefs.getStringSet("completed_missions", emptySet())?.toMutableSet() ?: mutableSetOf()
}

fun saveStarsCount(context: Context, stars: Int) {
    context.getSharedPreferences("achievements", Context.MODE_PRIVATE)
        .edit()
        .putInt("stars_count", stars)
        .apply()
}

fun loadStarsCount(context: Context): Int {
    val prefs = context.getSharedPreferences("achievements", Context.MODE_PRIVATE)
    return prefs.getInt("stars_count", 0)
}

@Composable
fun AchievementsScreen() {
    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf("achievements") }
    var starsCount by remember { mutableStateOf(loadStarsCount(context)) }
    val collectedMissions = remember { mutableStateOf(loadCompletedMissions(context)) }

    fun onMissionCollected(title: String, stars: Int) {
        if (!collectedMissions.value.contains(title)) {
            starsCount += stars
            collectedMissions.value.add(title)
            saveCompletedMissions(context, collectedMissions.value)
            saveStarsCount(context, starsCount)
        }
    }

    val availableMissions = listOf(
        Triple("Complete 7 habits", "1 star", 1),
        Triple("Make 15 days streak", "2 star", 2),
        Triple("Make 30 day streak", "3 star", 3)
    ).filterNot { collectedMissions.value.contains(it.first) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (currentScreen) {
            "home" -> HomeScreen()
            "achievements" -> {
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
                    ProductivitySection(starsCount)
                    Spacer(modifier = Modifier.height(16.dp))

                    if (availableMissions.isNotEmpty()) {
                        Text(
                            "Missions",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontFamily = Poppins
                        )
                        availableMissions.forEach { (title, starsLabel, starsValue) ->
                            MissionCard(title, starsLabel, false) {
                                onMissionCollected(title, starsValue)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    CompletedMissionsSection(collectedMissions.value)
                }
            }
            "profile" -> ProfileScreen(null)
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            BottomNavigationBar(
                currentScreen = currentScreen,
                onNavigate = { screen ->
                    currentScreen = screen
                }
            )
        }
    }
}

@Composable
fun HomeScreen() {
    // Add your home screen content here
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Home Screen")
    }
}

@Composable
fun MonthHeader() {
    val months =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH)
    val currentMonth = months[currentMonthIndex]
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            "Month",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(top = 22.dp, bottom = 16.dp),
            fontFamily = Poppins
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), contentAlignment = Alignment.Center) {
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
            currentYear.toString(),
            fontSize = 14.sp,
            color = Color.Black,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ProductivitySection(starsCount: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD983BB), RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                "Collect the stars and let's see how productive you are!",
                fontSize = 11.sp,
                color = Color.White,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))
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
                            "My Stars",
                            fontSize = 15.sp,
                            color = Color.White,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            starsCount.toString(),
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
                Column(modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)) {
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
fun MissionCard(title: String, stars: String, collected: Boolean, onCollect: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp))
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
            if (!collected) {
                OutlinedButton(
                    onClick = { onCollect() },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, Color(0xFFD983BB)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFFD983BB)
                    )
                ) {
                    Text("Collect", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun CompletedMissionsSection(collected: Set<String>) {
    if (collected.isNotEmpty()) {
        Text(
            "Completed Missions",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            fontFamily = Poppins
        )
        Spacer(modifier = Modifier.height(8.dp))
        for (title in collected) {
            MissionCard(title, "✔", collected = true) { }
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
