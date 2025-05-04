package com.elevate

import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elevate.components.BottomNavigationBar
import com.elevate.ui.theme.Poppins
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val preferences = remember { SharedPreferencesHelper(context) }
    var expandedHabit by remember { mutableStateOf<HabitUiData?>(null) }
    var selectedTab by remember { mutableStateOf(0) }
    
    // Get saved habits or use default list if none exist
    val savedHabits = remember { preferences.getHabits() }
    val habits = if (savedHabits.isEmpty()) {
        listOf(
            HabitUiData(
                name = "Exercise",
                timesPerDay = 2,
                imageRes = R.drawable.exercise
            ),
            HabitUiData(
                name = "Reading",
                timesPerDay = 2,
                imageRes = R.drawable.reading_illustration
            )
        )
    } else {
        savedHabits
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDF6F9))
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
                .padding(bottom = 72.dp)
        ) {
            GreetingSection(preferences.getUserName())
            Spacer(modifier = Modifier.height(16.dp))
            GoalsCard()
            Spacer(modifier = Modifier.height(16.dp))
            MyHabitsSection(
                habits = habits,
                expandedHabit = expandedHabit,
                onAddHabit = { 
                    context.startActivity(Intent(context, NewHabitActivity::class.java))
                },
                onHabitClick = { habit ->
                    expandedHabit = if (expandedHabit == habit) null else habit
                },
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
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

data class HabitUiData(val name: String, val timesPerDay: Int, val imageRes: Int)

@Composable
private fun GreetingSection(userName: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.hi_user, userName),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            color = Color(0xFF222222)
        )
        Text(
            text = stringResource(R.string.lets_be_productive_today),
            fontSize = 16.sp,
            fontFamily = Poppins,
            color = Color(0xFF737373)
        )
    }
}

@Composable
private fun GoalsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD983BB)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.your_goals),
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontFamily = Poppins
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.make_30_days_streak),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = Poppins,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.goals_illustration), // Replace with your drawable
                contentDescription = null,
                modifier = Modifier.size(160.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MyHabitsSection(
    habits: List<HabitUiData>,
    expandedHabit: HabitUiData?,
    onAddHabit: () -> Unit,
    onHabitClick: (HabitUiData) -> Unit,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val context = LocalContext.current
    val preferences = remember { SharedPreferencesHelper(context) }
    var habitList by remember { mutableStateOf(habits.toMutableList()) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.my_habits),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = onAddHabit,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD983BB)),
            shape = RoundedCornerShape(50)
        ) {
            Text(stringResource(R.string.add_habit), color = Color.White, fontFamily = Poppins)
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    Column {
        habitList.forEach { habit ->
            key(habit.name) {
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            habitList = habitList.filter { h -> h != habit }.toMutableList()
                            preferences.saveHabits(habitList)
                            true
                        } else false
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        val progress = dismissState.progress.fraction
                        if (progress > 0f) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.Red)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                IconButton(onClick = {
                                    habitList = habitList.filter { h -> h != habit }.toMutableList()
                                    preferences.saveHabits(habitList)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = stringResource(R.string.delete),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    },
                    dismissContent = {
                        HabitCard(
                            habit = habit,
                            expanded = expandedHabit == habit,
                            onClick = { onHabitClick(habit) },
                            selectedTab = selectedTab,
                            onTabSelected = onTabSelected
                        )
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun CalendarView() {
    val currentDate = LocalDate.now()
    val currentMonth = YearMonth.now()
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1)
    val firstDayOfWeek = if (firstDayOfMonth.dayOfWeek.value == 7) 0 else firstDayOfMonth.dayOfWeek.value

    // Do NOT use remember here, so it updates with locale
    val months = stringArrayResource(R.array.months)
    val monthName = months[currentMonth.monthValue - 1]
    val daysOfWeek = stringArrayResource(R.array.days_of_week)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Month and Year header
        Text(
            text = "$monthName ${currentMonth.year}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Days of week header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = Poppins,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Calendar grid
        val days = mutableListOf<Int>()
        // Add empty spaces for days before the first day of the month
        repeat(firstDayOfWeek) { days.add(0) }
        // Add the days of the month
        (1..daysInMonth).forEach { days.add(it) }

        // Create rows of 7 days each
        days.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                week.forEach { day ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (day > 0) {
                            val isToday = day == currentDate.dayOfMonth
                            Text(
                                text = day.toString(),
                                fontSize = 14.sp,
                                color = if (isToday) Color.White else Color.Black,
                                fontFamily = Poppins,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isToday) Color(0xFFD983BB)
                                        else Color.Transparent
                                    )
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HabitCard(
    habit: HabitUiData,
    expanded: Boolean,
    onClick: () -> Unit,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = habit.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(52.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = habit.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = Poppins
                    )
                    Text(
                        text = stringResource(R.string.habit_times_per_day, habit.timesPerDay),
                        fontSize = 14.sp,
                        color = Color(0xFF737373),
                        fontFamily = Poppins
                    )
                }
                Icon(
                    painter = painterResource(id = if (expanded) R.drawable.ic_dropdown else R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = Color(0xFFB0B0B0),
                    modifier = Modifier.size(24.dp)
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row {
                        TabButton(stringResource(R.string.today), selectedTab == 0) { onTabSelected(0) }
                        Spacer(modifier = Modifier.width(8.dp))
                        TabButton(stringResource(R.string.this_month), selectedTab == 1) { onTabSelected(1) }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (selectedTab == 0) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = 0.5f,
                            color = Color(0xFFF3B6D2),
                            strokeWidth = 8.dp,
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = "50%",
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            color = Color(0xFF222222),
                            fontFamily = Poppins
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.times_completed, 1, 2),
                        fontSize = 14.sp,
                        color = Color(0xFF222222),
                        fontFamily = Poppins,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    CalendarView()
                }
            }
        }
    }
}

@Composable
private fun TabButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFFF3B6D2) else Color(0xFFF5F5F5),
            contentColor = if (selected) Color.White else Color(0xFFB0B0B0)
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier.height(36.dp)
    ) {
        Text(text, fontFamily = Poppins)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
