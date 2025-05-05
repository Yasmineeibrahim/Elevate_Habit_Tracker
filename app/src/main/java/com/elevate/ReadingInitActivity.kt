package com.elevate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ReadingInitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reading_init)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.continue_button).setOnClickListener {
            continueToNext()
        }
    }

    private fun continueToNext() {
        val nextIndex = intent.getIntExtra("NEXT_INDEX", -1)
        val habitTitles = intent.getStringArrayListExtra("HABIT_LIST")

        if (habitTitles != null && nextIndex < habitTitles.size) {
            val nextTitle = habitTitles[nextIndex]
            val nextIntent = when (nextTitle) {
                "Reading" -> Intent(this, ReadingInitActivity::class.java)
                "Drinking water" -> Intent(this, DrinkingWaterActivity::class.java)
                "Sleeping Schedule" -> Intent(this, SleepingScheduleActivity::class.java)
                else -> Intent(this, WelcomeActivity::class.java)
            }

            nextIntent.putExtra("NEXT_INDEX", nextIndex + 1)
            nextIntent.putExtra("HABIT_LIST", habitTitles)
            startActivity(nextIntent)
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }

        finish()
    }
}
