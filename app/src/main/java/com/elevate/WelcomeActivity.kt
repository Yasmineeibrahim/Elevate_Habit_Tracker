package com.elevate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val continueButton: MaterialButton = findViewById(R.id.continue_button)


        continueButton.setOnClickListener {

            val intent = Intent(this, AchievementsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
