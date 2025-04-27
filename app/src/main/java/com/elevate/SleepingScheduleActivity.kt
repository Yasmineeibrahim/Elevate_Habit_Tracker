package com.elevate

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SleepingScheduleActivity :AppCompatActivity(){
    private lateinit var hoursInput:EditText
    private lateinit var wakeTimeInput:EditText
    private lateinit var continueButton:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleeping_schedule)
        hoursInput=findViewById(R.id.hoursInput)
        wakeTimeInput=findViewById(R.id.wakeTimeInput)
        continueButton=findViewById(R.id.continue_button)

        wakeTimeInput.setOnClickListener{
            showTimePickerDialog()
        }
        continueButton.setOnClickListener{
            val hours=hoursInput.text.toString()
            val wakeTime=wakeTimeInput.text.toString()

            if(hours.isEmpty()||wakeTime.isEmpty()){
                Toast.makeText(this, "please fill all required fields ", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "\"Saved:$hours hrs,wake:$wakeTime\"", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private fun showTimePickerDialog() {

    }
}