package com.elevate

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.button.MaterialButton

class DrinkingWaterActivity : AppCompatActivity(){
    private lateinit var litresInput:EditText
    private lateinit var startTimeInput:EditText
    private lateinit var endTimeInput:EditText
    private lateinit var continueButton:MaterialButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinking_water)

        litresInput=findViewById(R.id.inputLitres)
        startTimeInput=findViewById(R.id.inputStartTime)
        endTimeInput=findViewById(R.id.inputEndTime)
        continueButton=findViewById(R.id.continue_button)

        startTimeInput.setOnClickListener{
            showTimePickerDialog(startTimeInput)
        }
        endTimeInput.setOnClickListener{
            showTimePickerDialog(endTimeInput)
        }
        continueButton.setOnClickListener{
            validateInputs()
        }
    }

    private fun showTimePickerDialog(editText: EditText) {
        val calender =Calendar.getInstance()
        val hour=calender.get(Calendar.HOUR_OF_DAY)
        val minute=calender.get(Calendar.MINUTE)

        val timePickerDialog=TimePickerDialog(
            this,
            {_,selectedHour,selectedMinute ->
                val amPm = if(selectedHour>=12)"PM" else "AM"
                val hourFormatted=if(selectedHour%12==0) 12 else selectedHour%12
                val minuteFormatted=String.format("%02d",selectedMinute)
                editText.setText("$hourFormatted:$minuteFormatted$amPm")
            },
            hour,minute,false
        )
        timePickerDialog.show()
    }
    private fun validateInputs() {
        val litres = litresInput.text.toString()
        val startTime = startTimeInput.text.toString()

        if(litres.isEmpty()){
            litresInput.error="please enter how many litres"
            return
        }
        if(startTime.isEmpty()){
            startTimeInput.error="please select start time "
            return
        }
        Toast.makeText(this, "Habit saved successfully", Toast.LENGTH_SHORT).show();
        val intent= Intent(this,SleepingScheduleActivity::class.java)
        startActivity(intent)
    }
}