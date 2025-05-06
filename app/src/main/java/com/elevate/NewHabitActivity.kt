package com.elevate

import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.elevate.data.HabitEntity
import com.elevate.viewmodels.HabitViewModel
import com.google.android.material.button.MaterialButton

class NewHabitActivity : AppCompatActivity() {

    private lateinit var habitNameInput: EditText
    private lateinit var timesPerDayInput: EditText
    private lateinit var startTimeInput: EditText
    private lateinit var endTimeInput: EditText
    private lateinit var preferredTimeInput: EditText
    private lateinit var continueButton: MaterialButton
    private lateinit var habitImage: ImageView
    private lateinit var arrowLeft: ImageButton
    private lateinit var arrowRight: ImageButton
    private lateinit var habitViewModel: HabitViewModel
    private var currentImageIndex = 0
    private var habitName = ""

    // Add your drawable resource IDs here
    private val habitImages = arrayOf(
        R.drawable.curly,
        R.drawable.ic_exercise,
        R.drawable.ic_reading,
        R.drawable.prayer,
        R.drawable.journaling,
        R.drawable.sleeping,
        R.drawable.drinking
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_habit)

        habitNameInput = findViewById(R.id.inputHabitName)
        timesPerDayInput = findViewById(R.id.inputTimesPerDay)
        startTimeInput = findViewById(R.id.inputStartTime)
        endTimeInput = findViewById(R.id.inputEndTime)
        preferredTimeInput = findViewById(R.id.inputPreferredTime)
        continueButton = findViewById(R.id.continue_button)
        habitImage = findViewById(R.id.habitImage)
        arrowLeft = findViewById(R.id.arrowLeft)
        arrowRight = findViewById(R.id.arrowRight)

        habitViewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        updateHabitImage()

        arrowLeft.setOnClickListener {
            currentImageIndex =
                if (currentImageIndex - 1 < 0) habitImages.size - 1 else currentImageIndex - 1
            updateHabitImage()
        }
        arrowRight.setOnClickListener {
            currentImageIndex = (currentImageIndex + 1) % habitImages.size
            updateHabitImage()
        }

        startTimeInput.setOnClickListener {
            showTimePickerDialog(startTimeInput)
        }

        endTimeInput.setOnClickListener {
            showTimePickerDialog(endTimeInput)
        }

        preferredTimeInput.setOnClickListener {
            showTimePickerDialog(preferredTimeInput)
        }

        continueButton.setOnClickListener {
            validateAndSave()
        }
    }

    private fun updateHabitImage() {
        habitImage.setImageResource(habitImages[currentImageIndex])
    }

    private fun showTimePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val amPm = if (selectedHour >= 12) "PM" else "AM"
                val hourFormatted = if (selectedHour % 12 == 0) 12 else selectedHour % 12
                val minuteFormatted = String.format("%02d", selectedMinute)
                editText.setText("$hourFormatted:$minuteFormatted $amPm")
            },
            hour,
            minute,
            false
        )
        timePickerDialog.show()
    }

    private fun validateAndSave() {
        val habitName = habitNameInput.text.toString()
        val timesPerDay = timesPerDayInput.text.toString()
        val startTime = startTimeInput.text.toString()
        val endTime = endTimeInput.text.toString()
        val preferredTime = preferredTimeInput.text.toString()

        if (habitName.isEmpty()) {
            habitNameInput.error = "Please enter a habit name"
            return
        }

        if (timesPerDay.isEmpty()) {
            timesPerDayInput.error = "Please enter how many times per day"
            return
        }

        if (startTime.isEmpty()) {
            startTimeInput.error = "Please select a start time"
            return
        }

        // Save the habit to the Room database
        val habit = HabitEntity(
            userId = getUserId(),
            habitName = habitName,
            practiceTimes = timesPerDay.toIntOrNull() ?: 1,
            startTime = startTime,
            endTime = endTime.ifEmpty { "10:00 PM" },
            preferredTime = preferredTime.ifEmpty { null },
            isActive = true
        )
        habitViewModel.saveHabit(habit)

        Toast.makeText(this, "Habit added successfully", Toast.LENGTH_SHORT).show()

        // Return to HomeScreen
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun getUserId(): String {
        return getSharedPreferences("user_prefs", 0)
            .getString("user_id", "") ?: ""
    }
}
