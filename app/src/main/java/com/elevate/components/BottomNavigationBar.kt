package com.elevate.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elevate.R
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.utils.noRippleClickable

@Composable
fun BottomNavigationBar(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(getIndexForScreen(currentScreen)) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AnimatedNavigationBar(
            selectedIndex = selectedIndex,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .shadow(8.dp, RoundedCornerShape(36.dp)),
            ballColor = Color.White,
            barColor = Color(0xFFD983BB)
        ) {
            NavigationItem(
                icon = R.drawable.ic_home,
                label = stringResource(com.elevate.R.string.dashboard),
                isSelected = selectedIndex == 0
            ) {
                selectedIndex = 0
                onNavigate("home")
            }

            NavigationItem(
                icon = R.drawable.ic_achievements,
                label = stringResource(com.elevate.R.string.achievements_title),
                isSelected = selectedIndex == 1
            ) {
                selectedIndex = 1
                onNavigate("achievements")
            }

            NavigationItem(
                icon = R.drawable.person_icon,
                label = stringResource(com.elevate.R.string.profile_title),
                isSelected = selectedIndex == 2
            ) {
                selectedIndex = 2
                onNavigate("profile")
            }
        }
    }
}

@Composable
private fun NavigationItem(
    icon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(
                    if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

private fun getIndexForScreen(screen: String): Int {
    return when (screen) {
        "home" -> 0
        "achievements" -> 1
        "profile" -> 2
        else -> 0
    }
} 