package com.elevate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elevate.ui.theme.ElevateTheme
import com.elevate.ui.theme.Poppins
import com.elevate.utils.updateLocale
import com.elevate.components.BottomNavigationBar

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val viewModel = ProfileViewModel(applicationContext)
        
        setContent {
            ElevateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ProfileScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(viewModel: ProfileViewModel?) {
    val context = LocalContext.current
    val preferences = remember { SharedPreferencesHelper(context) }
    var notificationsEnabled by remember { mutableStateOf(viewModel?.notificationsEnabled ?: true) }
    var languageExpanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(listOf(Color(0xFFFFF0F5), Color.White)))
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD983BB))
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Lillie Brown", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("🏆 Ambassador", color = Color.Gray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatItem("112", "Total Stars")
                StatItem("627", "Streaks")
                StatItem("8", "Habits")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Notifications row with switch
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color(0xFFFF7EB6), shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text("Notifications", fontSize = 16.sp)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { enabled ->
                        notificationsEnabled = enabled
                        viewModel?.toggleNotifications(enabled)
                        preferences.setNotificationsEnabled(enabled)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFFFF7EB6),
                        uncheckedThumbColor = Color.Gray,
                        checkedTrackColor = Color(0xFFFE3473)
                    )
                )
            }

            // Language dropdown
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clickable { languageExpanded = !languageExpanded }
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color(0xFF9F7AEA), shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Language", fontSize = 16.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = if (languageExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }

                if (languageExpanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Arabic",
                            fontWeight = if (selectedLanguage == "Arabic") FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    selectedLanguage = "Arabic"
                                    preferences.setSelectedLanguage("Arabic")
                                    context.updateLocale("ar")
                                    languageExpanded = false
                                }
                        )
                        Text(
                            text = "English",
                            fontWeight = if (selectedLanguage == "English") FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    selectedLanguage = "English"
                                    preferences.setSelectedLanguage("English")
                                    context.updateLocale("en")
                                    languageExpanded = false
                                }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = {
                        preferences.clearAll()
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(Color(0xFF9F7AEA), Color(0xFFD983BB))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Logout",
                        color = Color.White,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            BottomNavigationBar(
                currentScreen = "profile",
                onNavigate = { /* Handle navigation if needed */ }
            )
        }
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
            Text(label, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ElevateTheme {
        ProfileScreen(viewModel = null)
    }
}
