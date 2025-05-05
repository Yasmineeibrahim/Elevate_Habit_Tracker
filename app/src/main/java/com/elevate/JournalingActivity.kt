package com.elevate


import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


class JournalingActivity : AppCompatActivity() {

    private lateinit var timesPerDayInput: EditText
    private lateinit var startTimeInput: EditText
    private lateinit var endTimeInput: EditText
    private lateinit var preferredTimeInput: EditText
    private lateinit var continueButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_habit) // Replace with your actual XML filename if different

        timesPerDayInput = findViewById(R.id.inputTimesPerDay)
        startTimeInput = findViewById(R.id.inputStartTime)
        endTimeInput = findViewById(R.id.inputEndTime)
        preferredTimeInput = findViewById(R.id.inputPreferredTime)
        continueButton = findViewById(R.id.continue_button)

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
        val timesPerDay = timesPerDayInput.text.toString()
        val startTime = startTimeInput.text.toString()


        if (timesPerDay.isEmpty()) {
            timesPerDayInput.error = "Please enter how many times per day"
            return
        }

        if (startTime.isEmpty()) {
            startTimeInput.error = "Please select a start time"
            return
        }


        val nextIndex = intent.getIntExtra("NEXT_INDEX", -1)
        val intent = Intent(this, TakeoffActivity::class.java)
        intent.putExtra("NEXT_INDEX", nextIndex)
        startActivity(intent)
        finish()


    }
}
