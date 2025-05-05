package com.elevate

import Habit
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TakeoffActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var nextButton: Button
    private val selectedHabitsInOrder = mutableListOf<Habit>()  // Stores selected habits in the order chosen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_takeoff)

        recyclerView = findViewById(R.id.habitsRecyclerView)
        nextButton = findViewById(R.id.nextButton)

        // List of all available habits
        val habitList = listOf(
            Habit("Exercise", R.drawable.exercise),
            Habit("Reading", R.drawable.reading),
            Habit("Journaling", R.drawable.journaling),
            Habit("Prayer", R.drawable.prayer),
            Habit("Drinking water", R.drawable.drinking_water),
            Habit("Sleeping Schedule", R.drawable.sleeping_schedule)
        )

        val adapter = HabitAdapter(habitList) { habit ->
            if (habit.isSelected) {
                if (!selectedHabitsInOrder.contains(habit)) {
                    selectedHabitsInOrder.add(habit)
                }
            } else {
                selectedHabitsInOrder.remove(habit)
            }
        }


        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns
        recyclerView.adapter = adapter

        nextButton.setOnClickListener {
            if (selectedHabitsInOrder.isEmpty()) {
                Toast.makeText(this, "Please select at least one habit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Start showing habits in the order selected
            showSelectedHabits(0)
        }
    }

    private fun showSelectedHabits(index: Int) {
        if (index >= selectedHabitsInOrder.size) {
            // Once all habits are completed, navigate to the WelcomeActivity
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
            return
        }

        val currentHabit = selectedHabitsInOrder[index]

        // Show the habit screen for the current habit
        when (currentHabit.title) {
            "Exercise" -> showHabitScreen(ExerciseActivity::class.java, index)
            "Reading" -> showHabitScreen(ReadingInitActivity::class.java, index)
            "Journaling" -> showHabitScreen(JournalingActivity::class.java, index)
            "Prayer" -> showHabitScreen(PrayerActivity::class.java, index)
            "Drinking water" -> showHabitScreen(DrinkingWaterActivity::class.java, index)
            "Sleeping Schedule" -> showHabitScreen(SleepingScheduleActivity::class.java, index)
            else -> showSelectedHabits(index + 1)
        }
    }

    private fun showHabitScreen(activityClass: Class<*>, index: Int) {
        val intent = Intent(this, activityClass)
        intent.putExtra("NEXT_INDEX", index + 1)
        intent.putParcelableArrayListExtra("SELECTED_HABITS", ArrayList(selectedHabitsInOrder))
        startActivity(intent)
        finish()
    }
}


