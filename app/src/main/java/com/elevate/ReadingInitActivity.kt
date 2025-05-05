package com.elevate

import Habit
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elevate.databinding.ActivityReadingInitBinding
import com.elevate.utils.Util.onNavigationToNextActivity

class ReadingInitActivity : AppCompatActivity() {

    private var nextIndex = -1
    private lateinit var selectedHabits: ArrayList<Habit>
    private lateinit var binding: ActivityReadingInitBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReadingInitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nextIndex = intent.getIntExtra("NEXT_INDEX", -1)
        selectedHabits =
            intent.getParcelableArrayListExtra<Habit>("SELECTED_HABITS") ?: arrayListOf()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.continueButton.setOnClickListener {
            continueToNext()
        }
    }

    private fun continueToNext() {
        onNavigationToNextActivity(nextIndex, selectedHabits)
    }
}
