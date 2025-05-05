package com.elevate


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PrayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prayer)

        // Define the list of options for spinner
        val yesNoOptions = listOf("Select an option", "Yes", "No")

        // Find the spinners and button
        val spinner1 = findViewById<Spinner>(R.id.yesNoSpinner)
        val spinner2 = findViewById<Spinner>(R.id.yesNoSpinner2)
        val spinner3 = findViewById<Spinner>(R.id.yesNoSpinner3)
        val continueBtn = findViewById<Button>(R.id.continue_button)

        // Create the ArrayAdapter and set it to the spinners
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, yesNoOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.adapter = adapter
        spinner2.adapter = adapter
        spinner3.adapter = adapter

        // Spinner 1 selection listener
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = yesNoOptions[position]
                if (position != 0) {
                    Toast.makeText(this@PrayerActivity, "You chose: $selected", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Spinner 2 selection listener
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = yesNoOptions[position]
                if (position != 0) {
                    Toast.makeText(this@PrayerActivity, "You chose: $selected", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Spinner 3 selection listener
        spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = yesNoOptions[position]
                if (position != 0) {
                    Toast.makeText(this@PrayerActivity, "You chose: $selected", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Continue button click listener
        continueBtn.setOnClickListener {
            // Get the selected values from all spinners
            val choice1 = spinner1.selectedItem.toString()
            val choice2 = spinner2.selectedItem.toString()
            val choice3 = spinner3.selectedItem.toString()

            // Check if any choice is still "Select an option"
            if (choice1 == "Select an option" || choice2 == "Select an option" || choice3 == "Select an option") {
                Toast.makeText(this, "Please answer all questions.", Toast.LENGTH_SHORT).show()
            } else {
                // If all choices are selected, log them and proceed
                Log.d(
                    "PrayerChoices",
                    "Track prayers: $choice1, Daily reminders: $choice2, Missed prayer reminders: $choice3"
                )
                // You can add code here to save or pass this data for further use
                val nextIndex = intent.getIntExtra("NEXT_INDEX", -1)
                val intent = Intent(this, TakeoffActivity::class.java)
                intent.putExtra("NEXT_INDEX", nextIndex)
                startActivity(intent)
                finish()
            }
        }
    }
}
