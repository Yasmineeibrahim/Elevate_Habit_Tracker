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
import androidx.compose.runtime.collectAsState
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
    val currentStreak = preferences.getCurrentStreak()

    // Get the user's completed habit count (total)
    val application = context.applicationContext as android.app.Application
    val habitViewModel = remember { com.elevate.viewmodels.HabitViewModel(application) }
    val habits by habitViewModel.getHabits().collectAsState(initial = emptyList())
    val completedHabitsCount = habits.count { it.isCompleted }

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
        Triple(R.string.mission_complete_5_habits, 1, "easy"),
        Triple(R.string.mission_complete_10_habits, 2, "medium"),
        Triple(R.string.mission_complete_20_habits, 3, "hard"),
        Triple(R.string.mission_complete_5_habits_one_day, 2, "medium"),
        Triple(R.string.mission_complete_10_habits_one_day, 3, "hard"),
        Triple(R.string.mission_try_new_habit, 1, "easy"),
        Triple(R.string.mission_complete_habit_3_days, 1, "easy"),
        Triple(R.string.mission_complete_habit_7_days, 2, "medium"),
        Triple(R.string.mission_complete_habit_14_days, 3, "hard"),
        Triple(R.string.mission_complete_all_habits_week, 3, "hard"),
        Triple(R.string.mission_complete_habit_before_8am, 1, "easy"),
        Triple(R.string.mission_complete_habit_after_8pm, 1, "easy"),
        Triple(R.string.mission_complete_habit_without_reminders, 2, "medium"),
        Triple(R.string.mission_complete_habit_21_days, 3, "hard"),
        Triple(R.string.mission_complete_habit_30_days, 3, "hard"),
        Triple(R.string.mission_complete_habit_60_days, 3, "hard"),
        Triple(R.string.mission_complete_habit_90_days, 3, "hard"),
        Triple(R.string.mission_complete_habit_6_months, 3, "hard"),
        Triple(R.string.mission_complete_habit_1_year, 3, "hard"),
        Triple(R.string.mission_complete_habit_weekend, 1, "easy"),
        Triple(R.string.mission_complete_habit_weekday, 1, "easy"),
        Triple(R.string.mission_complete_habit_morning, 1, "easy"),
        Triple(R.string.mission_complete_habit_bedtime, 1, "easy"),
        Triple(R.string.mission_complete_habit_with_friend, 1, "easy"),
        Triple(R.string.mission_complete_habit_outdoors, 1, "easy"),
        Triple(R.string.mission_complete_habit_indoors, 1, "easy"),
        Triple(R.string.mission_complete_habit_on_holiday, 1, "easy"),
        Triple(R.string.mission_complete_habit_while_traveling, 2, "medium"),
        Triple(R.string.mission_complete_habit_lunchtime, 1, "easy"),
        Triple(R.string.mission_complete_two_habits_one_day, 2, "medium"),
        Triple(R.string.mission_track_habit_no_miss, 2, "medium"),
        Triple(R.string.mission_complete_habit_21_days, 3, "hard"),
        Triple(R.string.mission_complete_habit_30_days, 3, "hard"),
        Triple(R.string.mission_complete_habit_60_days, 3, "hard"),
        Triple(R.string.mission_complete_habit_90_days, 3, "hard"),
        Triple(R.string.mission_complete_habit_6_months, 3, "hard"),
        Triple(R.string.mission_complete_habit_1_year, 3, "hard"),
        Triple(R.string.mission_complete_habit_weekend, 1, "easy"),
        Triple(R.string.mission_complete_habit_weekday, 1, "easy"),
        Triple(R.string.mission_complete_habit_morning, 1, "easy"),
        Triple(R.string.mission_complete_habit_bedtime, 1, "easy"),

// Streaks
        Triple(R.string.mission_maintain_3_day_streak, 1, "easy"),
        Triple(R.string.mission_maintain_5_day_streak, 1, "easy"),
        Triple(R.string.mission_maintain_7_day_streak, 2, "medium"),
        Triple(R.string.mission_maintain_10_day_streak, 2, "medium"),
        Triple(R.string.mission_maintain_14_day_streak, 2, "medium"),
        Triple(R.string.mission_maintain_21_day_streak, 3, "hard"),
        Triple(R.string.mission_maintain_30_day_streak, 3, "hard"),
        Triple(R.string.mission_maintain_60_day_streak, 3, "hard"),
        Triple(R.string.mission_maintain_90_day_streak, 3, "hard"),
        Triple(R.string.mission_maintain_6_month_streak, 3, "hard"),
        Triple(R.string.mission_maintain_1_year_streak, 3, "hard"),
        Triple(R.string.mission_start_new_streak, 2, "medium"),
        Triple(R.string.mission_double_best_streak, 3, "hard"),

// Star Milestones
        Triple(R.string.mission_collect_10_stars, 1, "easy"),
        Triple(R.string.mission_collect_25_stars, 2, "medium"),
        Triple(R.string.mission_collect_50_stars, 3, "hard"),
        Triple(R.string.mission_collect_75_stars, 3, "hard"),
        Triple(R.string.mission_collect_100_stars, 3, "hard"),
        Triple(R.string.mission_collect_150_stars, 3, "hard"),
        Triple(R.string.mission_collect_200_stars, 3, "hard"),
        Triple(R.string.mission_collect_300_stars, 3, "hard"),
        Triple(R.string.mission_collect_400_stars, 3, "hard"),
        Triple(R.string.mission_collect_500_stars, 3, "hard"),

// Wellness & Productivity
        Triple(R.string.mission_drink_water_week, 1, "easy"),
        Triple(R.string.mission_read_20min_10days, 2, "medium"),
        Triple(R.string.mission_journal_14_days, 2, "medium"),
        Triple(R.string.mission_sleep_before_11pm_10days, 2, "medium"),
        Triple(R.string.mission_exercise_3x_week_month, 3, "hard"),
        Triple(R.string.mission_pray_week, 1, "easy"),
        Triple(R.string.mission_wake_before_7am_5days, 1, "easy"),
        Triple(R.string.mission_meditate_10min_10days, 2, "medium"),
        Triple(R.string.mission_no_social_media_3days, 2, "medium"),
        Triple(R.string.mission_gratitude_7days, 1, "easy"),
        Triple(R.string.mission_plan_day_10days, 2, "medium"),
        Triple(R.string.mission_no_complaining_day, 2, "medium"),
        Triple(R.string.mission_write_letter_future_self, 1, "easy"),
        Triple(R.string.mission_unplug_screens_day, 2, "medium"),
        Triple(R.string.mission_set_new_goal, 1, "easy"),
        Triple(R.string.mission_complete_habit_with_friend, 1, "easy"),
        Triple(R.string.mission_complete_habit_outdoors, 1, "easy"),
        Triple(R.string.mission_complete_habit_indoors, 1, "easy"),
        Triple(R.string.mission_complete_habit_on_holiday, 1, "easy"),
        Triple(R.string.mission_complete_habit_while_traveling, 2, "medium"),
        Triple(R.string.mission_complete_habit_lunchtime, 1, "easy"),
        Triple(R.string.mission_complete_habit_after_8pm, 1, "easy"),
        Triple(R.string.mission_complete_two_habits_one_day, 2, "medium"),
        Triple(R.string.mission_track_habit_no_miss, 2, "medium"),
        Triple(R.string.mission_complete_habit_before_8am, 1, "easy")

    )
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val shuffledMissions = remember(currentYear) {
        missionTemplates.shuffled(Random(currentYear))
    }
    val allMissions = shuffledMissions.map { (nameResId, stars, _) ->
        Triple(stringResource(nameResId as Int), "$stars star${if (stars > 1) "s" else ""}", stars)
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
                            val isStarMission = title.startsWith("Collect") && title.contains("stars")
                            val isStreakMission = title.contains("streak", ignoreCase = true)
                            val isHabitMission = title.startsWith("Complete") && title.contains("habit", ignoreCase = true)
                            val requiredStars = if (isStarMission) {
                                Regex("\\d+").find(title)?.value?.toIntOrNull() ?: 0
                            } else 0
                            val requiredStreak = if (isStreakMission) {
                                Regex("\\d+").find(title)?.value?.toIntOrNull() ?: 0
                            } else 0
                            val requiredHabits = if (isHabitMission) {
                                Regex("\\d+").find(title)?.value?.toIntOrNull() ?: 0
                            } else 0
                            val canCollect = when {
                                isStarMission -> starsCount >= requiredStars
                                isStreakMission -> currentStreak >= requiredStreak
                                isHabitMission -> completedHabitsCount >= requiredHabits
                                else -> true
                            }

                            MissionCard(
                                title,
                                stringResource(R.string.achievements_get_stars, starsLabel),
                                false,
                                enabled = canCollect
                            ) {
                                onMissionCollected(title, starsValue)
                            }
                        }
                        if (availableMissions.size > 3) {
                            Text(
                                text = stringResource(if (missionsExpanded) R.string.collapse else R.string.view),
                                color = Color(0xFFD983BB),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable { missionsExpanded = !missionsExpanded }
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
    val context = LocalContext.current
    val resources = context.resources
    val (productivityRes, motivationRes) = when {
        starsCount > 100 -> R.string.achievements_productivity_high to R.string.achievements_motivation_high
        starsCount > 50 -> R.string.achievements_productivity_moderate to R.string.achievements_motivation_moderate
        else -> R.string.achievements_productivity_low to R.string.achievements_motivation_low
    }

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
                        stringResource(productivityRes),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        stringResource(motivationRes),
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
fun MissionCard(title: String, stars: String, collected: Boolean, enabled: Boolean = true, onCollect: () -> Unit) {
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
                    onClick = { if (enabled) onCollect() },
                    enabled = enabled,
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, if (enabled) Color(0xFFD983BB) else Color.Gray),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (enabled) Color.White else Color.LightGray,
                        contentColor = if (enabled) Color(0xFFD983BB) else Color.Gray
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
