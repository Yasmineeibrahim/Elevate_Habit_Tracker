package com.elevate

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elevate.components.BottomNavigationBar
import com.elevate.ui.theme.Poppins

@Composable
fun HomeScreen() {
    var expandedHabit by remember { mutableStateOf<HabitUiData?>(null) }
    var selectedTab by remember { mutableStateOf(0) }
    val habits = listOf(
        HabitUiData(
            name = "Excercise",
            frequency = "2 times a day",
            imageRes = R.drawable.exercise // Replace with your drawable
        ),
        HabitUiData(
            name = "Reading",
            frequency = "2 times a day",
            imageRes = R.drawable.reading_illustration // Replace with your drawable
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDF6F9))
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
                .padding(bottom = 72.dp)
        ) {
            GreetingSection()
            Spacer(modifier = Modifier.height(16.dp))
            GoalsCard()
            Spacer(modifier = Modifier.height(16.dp))
            MyHabitsSection(
                habits = habits,
                expandedHabit = expandedHabit,
                onAddHabit = { /* TODO: Add Habit */ },
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

data class HabitUiData(val name: String, val frequency: String, val imageRes: Int)

@Composable
private fun GreetingSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Hi User,",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            color = Color(0xFF222222)
        )
        Text(
            text = "Lets be productive Today!",
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
                    text = "Your Goals",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontFamily = Poppins
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Make\n30 days\nstreak!",
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

@Composable
private fun MyHabitsSection(
    habits: List<HabitUiData>,
    expandedHabit: HabitUiData?,
    onAddHabit: () -> Unit,
    onHabitClick: (HabitUiData) -> Unit,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "My Habits",
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
            Text("+ Add Habit", color = Color.White, fontFamily = Poppins)
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    Column {
        habits.forEach { habit ->
            HabitCard(
                habit = habit,
                expanded = expandedHabit == habit,
                onClick = { onHabitClick(habit) },
                selectedTab = selectedTab,
                onTabSelected = onTabSelected
            )
            Spacer(modifier = Modifier.height(12.dp))
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
                        text = habit.frequency,
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
                Row {
                    TabButton("Today", selectedTab == 0) { onTabSelected(0) }
                    Spacer(modifier = Modifier.width(8.dp))
                    TabButton("This Month", selectedTab == 1) { onTabSelected(1) }
                }
                Spacer(modifier = Modifier.height(16.dp))
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
                    text = "1/2 times completed",
                    fontSize = 14.sp,
                    color = Color(0xFF222222),
                    fontFamily = Poppins,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
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
