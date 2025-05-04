package com.elevate
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Move navigation logic here, before setContent
        startActivity(Intent(this, SplashScreen1::class.java))
        finish()
        // Do not call setContent if navigating away
        // setContent {
        //     MaterialTheme {
        //         // Your Compose content
        //     }
        // }

        // Initialize Facebook SDK
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(application)
    }
}