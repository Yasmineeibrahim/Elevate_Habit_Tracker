package com.elevate
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Move navigation logic here, before setContent
        startActivity(Intent(this, TakeoffActivity::class.java))
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