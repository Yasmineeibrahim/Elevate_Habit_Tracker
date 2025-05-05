package com.elevate

import Habit
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TakeoffActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var nextButton: Button
    private lateinit var fab: FloatingActionButton
    private val selectedHabitsInOrder = mutableListOf<Habit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_takeoff)

        recyclerView = findViewById(R.id.habitsRecyclerView)
        nextButton = findViewById(R.id.nextButton)
        fab = findViewById(R.id.fab)

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


        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        onClicks()
    }

    private fun onClicks() {
        nextButton.setOnClickListener {
            if (selectedHabitsInOrder.isEmpty()) {
                Toast.makeText(this, "Please select at least one habit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Start showing habits in the order selected
            showSelectedHabits()
        }

        fab.setOnClickListener {
            val intent = Intent(this, NewHabitActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showSelectedHabits() {
        val currentHabit = selectedHabitsInOrder.first()

        // Show the habit screen for the current habit
        when (currentHabit.title) {
            "Exercise" -> showHabitScreen(ExerciseActivity::class.java)
            "Reading" -> showHabitScreen(ReadingInitActivity::class.java)
            "Journaling" -> showHabitScreen(JournalingActivity::class.java)
            "Prayer" -> showHabitScreen(PrayerActivity::class.java)
            "Drinking water" -> showHabitScreen(DrinkingWaterActivity::class.java)
            "Sleeping Schedule" -> showHabitScreen(SleepingScheduleActivity::class.java)
            else -> Unit
        }
    }

    private fun showHabitScreen(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.putExtra("NEXT_INDEX", 1)
        intent.putParcelableArrayListExtra("SELECTED_HABITS", ArrayList(selectedHabitsInOrder))
        startActivity(intent)
    }
}


