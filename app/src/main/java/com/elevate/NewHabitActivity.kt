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
    private var currentImageIndex = 0

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

        continueButton.setOnClickListener {
            validateAndProceed()
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

    private fun validateAndProceed() {
        val habitName = habitNameInput.text.toString()
        val timesPerDay = timesPerDayInput.text.toString()
        val startTime = startTimeInput.text.toString()

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

        // Create new habit with selected image
        val newHabit = HabitUiData(
            name = habitName,
            timesPerDay = timesPerDay.toIntOrNull() ?: 1,
            imageRes = habitImages[currentImageIndex]
        )

        // Save the habit
        val preferences = SharedPreferencesHelper(this)
        preferences.addHabit(newHabit)

        Toast.makeText(this, "Habit added successfully", Toast.LENGTH_SHORT).show()

        // Return to MainActivity which hosts the HomeScreen
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}
