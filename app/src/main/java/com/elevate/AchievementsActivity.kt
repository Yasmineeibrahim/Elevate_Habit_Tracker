package com.elevate

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.elevate.components.BottomNavigationBar
import com.elevate.ui.theme.ElevateTheme
import com.elevate.ui.theme.Poppins
import com.elevate.utils.SharedPreferencesHelper
import java.util.Calendar
import kotlin.random.Random

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
    val preferences = remember { SharedPreferencesHelper(context) }
    var currentScreen by remember { mutableStateOf("achievements") }
    var starsCount by remember { mutableStateOf(preferences.getStarsCount()) }
    val collectedMissionsState = remember { mutableStateOf(loadCompletedMissions(context)) }
    val collectedMissions = collectedMissionsState.value

    fun onMissionCollected(title: String, stars: Int) {
        if (!collectedMissions.contains(title)) {
            val newStarsCount = starsCount + stars
            val newCollectedMissions = collectedMissions.toMutableSet().apply {
                add(title)
            }
            starsCount = newStarsCount
            collectedMissionsState.value = newCollectedMissions
            saveCompletedMissions(context, newCollectedMissions)
            preferences.setStarsCount(newStarsCount)
        }
    }

    // --- 85 Unique, Meaningful Missions List ---
    val missionTemplates = listOf(
        // General Habit Completions
        Triple("Complete 5 habits", 1, "easy"),
        Triple("Complete 10 habits", 2, "medium"),
        Triple("Complete 20 habits", 3, "hard"),
        Triple("Complete 5 habits in one day", 2, "medium"),
        Triple("Complete 10 habits in one day", 3, "hard"),
        Triple("Try a new habit", 1, "easy"),
        Triple("Complete a habit for 3 consecutive days", 1, "easy"),
        Triple("Complete a habit for 7 consecutive days", 2, "medium"),
        Triple("Complete a habit for 14 consecutive days", 3, "hard"),
        Triple("Complete all habits for a week", 3, "hard"),
        Triple("Complete a habit before 8am", 1, "easy"),
        Triple("Complete a habit after 8pm", 1, "easy"),
        Triple("Complete a habit without reminders", 2, "medium"),
        Triple("Complete a habit for 21 days", 3, "hard"),
        Triple("Complete a habit for 30 days", 3, "hard"),
        Triple("Complete a habit for 60 days", 3, "hard"),
        Triple("Complete a habit for 90 days", 3, "hard"),
        Triple("Complete a habit for 6 months", 3, "hard"),
        Triple("Complete a habit for 1 year", 3, "hard"),
        Triple("Complete a habit during the weekend", 1, "easy"),
        Triple("Complete a habit during the weekday", 1, "easy"),
        Triple("Complete a habit in the morning", 1, "easy"),
        Triple("Complete a habit at bedtime", 1, "easy"),
        // Streaks
        Triple("Maintain a 3-day streak", 1, "easy"),
        Triple("Maintain a 5-day streak", 1, "easy"),
        Triple("Maintain a 7-day streak", 2, "medium"),
        Triple("Maintain a 10-day streak", 2, "medium"),
        Triple("Maintain a 14-day streak", 2, "medium"),
        Triple("Maintain a 21-day streak", 3, "hard"),
        Triple("Maintain a 30-day streak", 3, "hard"),
        Triple("Maintain a 60-day streak", 3, "hard"),
        Triple("Maintain a 90-day streak", 3, "hard"),
        Triple("Maintain a 6-month streak", 3, "hard"),
        Triple("Maintain a 1-year streak", 3, "hard"),
        Triple("Start a new streak after breaking one", 2, "medium"),
        Triple("Double your previous best streak", 3, "hard"),
        // Star Milestones
        Triple("Collect 10 stars", 1, "easy"),
        Triple("Collect 25 stars", 2, "medium"),
        Triple("Collect 50 stars", 3, "hard"),
        Triple("Collect 75 stars", 3, "hard"),
        Triple("Collect 100 stars", 3, "hard"),
        Triple("Collect 150 stars", 3, "hard"),
        Triple("Collect 200 stars", 3, "hard"),
        Triple("Collect 300 stars", 3, "hard"),
        Triple("Collect 400 stars", 3, "hard"),
        Triple("Collect 500 stars", 3, "hard"),
        // Wellness & Productivity
        Triple("Drink 2L of water every day for a week", 1, "easy"),
        Triple("Read for 20 minutes daily for 10 days", 2, "medium"),
        Triple("Journal every day for 14 days", 2, "medium"),
        Triple("Go to bed before 11pm for 10 days", 2, "medium"),
        Triple("Exercise 3 times a week for a month", 3, "hard"),
        Triple("Pray every day for a week", 1, "easy"),
        Triple("Wake up before 7am for 5 days", 1, "easy"),
        Triple("Meditate for 10 minutes daily for 10 days", 2, "medium"),
        Triple("No social media for 3 days", 2, "medium"),
        Triple("Write a gratitude list for 7 days", 1, "easy"),
        Triple("Plan your day every morning for 10 days", 2, "medium"),
        Triple("Spend a day without complaining", 2, "medium"),
        Triple("Write a letter to your future self", 1, "easy"),
        Triple("Unplug from all screens for a day", 2, "medium"),
        Triple("Set a new personal goal", 1, "easy"),
        Triple("Complete a habit with a friend", 1, "easy"),
        Triple("Complete a habit outdoors", 1, "easy"),
        Triple("Complete a habit indoors", 1, "easy"),
        Triple("Complete a habit on a holiday", 1, "easy"),
        Triple("Complete a habit while traveling", 2, "medium"),
        Triple("Complete a habit at lunchtime", 1, "easy"),
        Triple("Complete a habit after 8pm", 1, "easy"),
        Triple("Complete two habits in a day", 2, "medium"),
        Triple("Track your habit without missing a day", 2, "medium"),
        Triple("Complete a habit before 8am", 1, "easy"),

        )
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val shuffledMissions = remember(currentYear) {
        missionTemplates.shuffled(Random(currentYear))
    }
    val allMissions = shuffledMissions.map { (name, stars, _) ->
        Triple(name, "$stars star${if (stars > 1) "s" else ""}", stars)
    }

    // --- Calculate missions for the current month ---
    val missionsPerMonth = 7
    val currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH) // 0-based
    val startIdx = (currentMonthIndex * missionsPerMonth) % allMissions.size
    val endIdx = (startIdx + missionsPerMonth)
    val missionsForMonth = if (endIdx <= allMissions.size) {
        allMissions.subList(startIdx, endIdx)
    } else {
        // Wrap around if needed
        allMissions.subList(startIdx, allMissions.size) + allMissions.subList(0, endIdx - allMissions.size)
    }
    // Filter out collected missions for this month
    val availableMissions = missionsForMonth.filterNot { collectedMissions.contains(it.first) }

    var missionsExpanded by remember { mutableStateOf(false) }
    val missionsToShow = if (missionsExpanded) availableMissions else availableMissions.take(3)

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
                            stringResource(R.string.achievements_missions),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontFamily = Poppins
                        )
                        missionsToShow.forEach { (title, starsLabel, starsValue) ->
                            MissionCard(
                                title,
                                stringResource(R.string.achievements_get_stars, starsLabel),
                                false
                            ) {
                                onMissionCollected(title, starsValue)
                            }
                        }
                        if (availableMissions.size > 3) {
                            Text(
                                text = if (missionsExpanded) "Collapse" else "View",
                                color = Color(0xFFD983BB),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable{ missionsExpanded = !missionsExpanded }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    CompletedMissionsSection(collectedMissions)
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
fun MonthHeader() {
    val months = stringArrayResource(R.array.months)
    val currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH)
    val currentMonth = months[currentMonthIndex]
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            stringResource(R.string.achievements_month),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(top = 22.dp, bottom = 16.dp),
            fontFamily = Poppins
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp), contentAlignment = Alignment.Center
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
                stringResource(R.string.achievements_collect_stars),
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
                            stringResource(R.string.achievements_my_stars),
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
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        stringResource(R.string.achievements_productivity_low),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        stringResource(R.string.achievements_keep_spirit),
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
                Text(stars, fontSize = 12.sp, color = Color.Gray)
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
                    Text(stringResource(R.string.achievements_collect), fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun CompletedMissionsSection(collected: Set<String>) {
    if (collected.isNotEmpty()) {
        Text(
            stringResource(R.string.achievements_completed_missions),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            fontFamily = Poppins
        )
        Spacer(modifier = Modifier.height(8.dp))
        for (title in collected) {
            MissionCard(
                title,
                stringResource(R.string.achievements_collected),
                collected = true
            ) { }
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
