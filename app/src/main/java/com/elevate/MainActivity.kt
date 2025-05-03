package com.elevate

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            MaterialTheme {
                // Navigate to AchievementsActivity
                startActivity(Intent(this, AchievementsActivity::class.java))
                finish()
            }
        }
        
        // Initialize Facebook SDK
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(application)
    }
}