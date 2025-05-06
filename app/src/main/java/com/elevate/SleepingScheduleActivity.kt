package com.elevate

import Habit
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.elevate.data.HabitEntity
import com.elevate.utils.Util.onNavigationToNextActivity
import com.elevate.viewmodels.HabitViewModel

class SleepingScheduleActivity : AppCompatActivity() {
    private lateinit var hoursInput: EditText
    private lateinit var wakeTimeInput: EditText
    private lateinit var continueButton: Button
    private var nextIndex = -1
    private lateinit var selectedHabits: ArrayList<Habit>
    private lateinit var habitViewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleeping_schedule)

        habitViewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        nextIndex = intent.getIntExtra("NEXT_INDEX", -1)
        selectedHabits = intent.getParcelableArrayListExtra<Habit>("SELECTED_HABITS") ?: arrayListOf()

        hoursInput = findViewById(R.id.hoursInput)
        wakeTimeInput = findViewById(R.id.wakeTimeInput)
        continueButton = findViewById(R.id.continue_button)

        wakeTimeInput.setOnClickListener {
            showTimePickerDialog()
        }
        continueButton.setOnClickListener {
            validateAndProceed()
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val amPm = if (selectedHour >= 12) "PM" else "AM"
                val hourFormatted = if (selectedHour % 12 == 0) 12 else selectedHour % 12
                val minuteFormatted = String.format("%02d", selectedMinute)
                wakeTimeInput.setText("$hourFormatted:$minuteFormatted $amPm")
            },
            hour,
            minute,
            false
        )
        timePickerDialog.show()
    }

    private fun validateAndProceed() {
        val hours = hoursInput.text.toString()
        val wakeTime = wakeTimeInput.text.toString()

        if (hours.isEmpty() || wakeTime.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Update the habit in the database
        val habit = HabitEntity(
            userId = getUserId(),
            habitName = "Sleeping Schedule",
            practiceTimes = hours.toIntOrNull() ?: 8,
            startTime = "10:00 PM", // Default start time
            endTime = wakeTime,
            preferredTime = null
        )
        habitViewModel.saveHabit(habit)

        Toast.makeText(this, "Saved: $hours hrs, wake: $wakeTime", Toast.LENGTH_SHORT).show()
        onNavigationToNextActivity(nextIndex, selectedHabits)
    }

    private fun getUserId(): String {
        return getSharedPreferences("user_prefs", 0)
            .getString("user_id", "") ?: ""
    }
}