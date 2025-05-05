package com.elevate

import Habit
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elevate.utils.Util.onNavigationToNextActivity


class SleepingScheduleActivity : AppCompatActivity() {
    private lateinit var hoursInput: EditText
    private lateinit var wakeTimeInput: EditText
    private lateinit var continueButton: Button

    private var nextIndex = -1
    private lateinit var selectedHabits: ArrayList<Habit>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleeping_schedule)

        nextIndex = intent.getIntExtra("NEXT_INDEX", -1)
        selectedHabits =
            intent.getParcelableArrayListExtra<Habit>("SELECTED_HABITS") ?: arrayListOf()


        hoursInput = findViewById(R.id.hoursInput)
        wakeTimeInput = findViewById(R.id.wakeTimeInput)
        continueButton = findViewById(R.id.continue_button)

        wakeTimeInput.setOnClickListener {
            showTimePickerDialog()
        }
        continueButton.setOnClickListener {
            val hours = hoursInput.text.toString()
            val wakeTime = wakeTimeInput.text.toString()

            if (hours.isEmpty() || wakeTime.isEmpty()) {
                Toast.makeText(this, "please fill all required fields ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "\"Saved:$hours hrs,wake:$wakeTime\"", Toast.LENGTH_SHORT)
                    .show();
            }

            onNavigationToNextActivity(nextIndex, selectedHabits)
        }
    }

    private fun showTimePickerDialog() {

    }
}