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
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ProfileViewModel(applicationContext)
        FirebaseFirestore.setLoggingEnabled(true)
        setContent {
            ->
            MaterialTheme {
                ProfileScreen(viewModel)
                enableEdgeToEdge()
                setContentView(R.layout.activity_main)

                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    v.setPadding(
                        systemBars.left,
                        systemBars.top,
                        systemBars.right,
                        systemBars.bottom
                    )
                    insets
                }
                val intent = Intent(this, AchievementsActivity::class.java)
                startActivity(intent)
                FacebookSdk.sdkInitialize(applicationContext)
                AppEventsLogger.activateApp(application)
            }
        }}}