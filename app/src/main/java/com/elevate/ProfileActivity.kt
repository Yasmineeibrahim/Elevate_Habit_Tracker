package com.elevate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elevate.components.BottomNavigationBar
import com.elevate.ui.theme.ElevateTheme
import com.elevate.ui.theme.Poppins
import com.elevate.utils.LocaleUtils
import com.elevate.utils.SharedPreferencesHelper
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.elevate.viewmodels.HabitViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val preferences = SharedPreferencesHelper(this)
        val savedLanguage = preferences.getSelectedLanguage()
        if (savedLanguage == "Arabic") {
            com.elevate.utils.LocaleUtils.setLocale(this, "ar")
        } else {
            com.elevate.utils.LocaleUtils.setLocale(this, "en")
        }
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
    val application = context.applicationContext as android.app.Application
    val habitViewModel = remember { HabitViewModel(application) }
    val habits by habitViewModel.getActiveHabits().collectAsStateWithLifecycle(initialValue = emptyList())
    var notificationsEnabled by remember { mutableStateOf(viewModel?.notificationsEnabled ?: true) }
    var selectedLanguage by remember { mutableStateOf(preferences.getSelectedLanguage()) }
    var languageExpanded by remember { mutableStateOf(false) }
    val currentLocale = context.resources.configuration.locales[0].language
    
    // Add state for stars count with initial value from achievements
    var starsCount by remember { mutableStateOf(0) }
    
    // Effect to update stars count when the screen is first displayed
    LaunchedEffect(Unit) {
        starsCount = preferences.getStarsCount()
    }
    
    // Effect to update stars count when habits change
    LaunchedEffect(habits) {
        starsCount = preferences.getStarsCount()
    }

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

            val composition by rememberLottieComposition(
                LottieCompositionSpec.Url("https://lottie.host/3493e215-7855-4434-8d57-13b8e91fafcb/hKS6lUDY5n.lottie")
            )
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                speed = 0.5f
            )
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent)
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(preferences.getUserName(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(stringResource(R.string.profile_ambassador), color = Color.Gray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatItem(starsCount.toString(), stringResource(R.string.profile_total_stars))
                StatItem(preferences.getCurrentStreak().toString(), stringResource(R.string.profile_streaks))
                StatItem(habits.size.toString(), stringResource(R.string.profile_habits))
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
                Text(stringResource(R.string.profile_notifications), fontSize = 16.sp)
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
                    Text(stringResource(R.string.language), fontSize = 16.sp)
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
                            text = stringResource(R.string.arabic),
                            fontWeight = if (currentLocale == "ar") FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    selectedLanguage = "Arabic"
                                    preferences.setSelectedLanguage("Arabic")
                                    LocaleUtils.setLocale(context, "ar")
                                    languageExpanded = false
                                    (context as? android.app.Activity)?.let {
                                        LocaleUtils.recreateActivity(
                                            it
                                        )
                                    }
                                }
                        )
                        Text(
                            text = stringResource(R.string.english),
                            fontWeight = if (currentLocale == "en") FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    selectedLanguage = "English"
                                    preferences.setSelectedLanguage("English")
                                    LocaleUtils.setLocale(context, "en")
                                    languageExpanded = false
                                    (context as? android.app.Activity)?.let {
                                        LocaleUtils.recreateActivity(
                                            it
                                        )
                                    }
                                }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = {
                    // Clear all user data
                    preferences.clearAll()
                    
                    // Sign out from Firebase
                    FirebaseAuth.getInstance().signOut()
                    
                    // Navigate to login screen
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
                        stringResource(R.string.profile_logout),
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
