package com.elevate


import Habit
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elevate.databinding.ActivityPrayerBinding
import com.elevate.utils.Util.onNavigationToNextActivity

class PrayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrayerBinding
    private var nextIndex = -1
    private lateinit var selectedHabits: ArrayList<Habit>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nextIndex = intent.getIntExtra("NEXT_INDEX", -1)
        selectedHabits =
            intent.getParcelableArrayListExtra<Habit>("SELECTED_HABITS") ?: arrayListOf()

        // Define the list of options for spinner
        val yesNoOptions = listOf("Select an option", "Yes", "No")

        // Create the ArrayAdapter and set it to the spinners
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, yesNoOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.apply {
            yesNoSpinner.adapter = adapter
            yesNoSpinner2.adapter = adapter
            yesNoSpinner3.adapter = adapter

            // Spinner 1 selection listener
            yesNoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selected = yesNoOptions[position]
                    if (position != 0) {
                        Toast.makeText(
                            this@PrayerActivity,
                            "You chose: $selected",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            // Spinner 2 selection listener
            yesNoSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selected = yesNoOptions[position]
                    if (position != 0) {
                        Toast.makeText(
                            this@PrayerActivity,
                            "You chose: $selected",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            // Spinner 3 selection listener
            yesNoSpinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selected = yesNoOptions[position]
                    if (position != 0) {
                        Toast.makeText(
                            this@PrayerActivity,
                            "You chose: $selected",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }

        // Continue button click listener
        onClick()
    }

    private fun onClick() {
        binding.continueButton.setOnClickListener {
            // Get the selected values from all spinners
            val choice1 = binding.yesNoSpinner.selectedItem.toString()
            val choice2 = binding.yesNoSpinner2.selectedItem.toString()
            val choice3 = binding.yesNoSpinner3.selectedItem.toString()

            // Check if any choice is still "Select an option"
            if (choice1 == "Select an option" || choice2 == "Select an option" || choice3 == "Select an option") {
                Toast.makeText(this, "Please answer all questions.", Toast.LENGTH_SHORT).show()
            } else onNavigationToNextActivity(nextIndex, selectedHabits)
        }
    }
}
