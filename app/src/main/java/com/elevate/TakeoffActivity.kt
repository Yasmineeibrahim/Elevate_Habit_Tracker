package com.elevate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TakeoffActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: ImageButton
    private lateinit var nextButton: Button
    private val selectedHabits = mutableListOf<Habit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_takeoff)

        recyclerView = findViewById(R.id.habitsRecyclerView)
        fab = findViewById(R.id.fab)
        nextButton = findViewById(R.id.nextButton)

        val habitList = listOf(
            Habit("Exercise", R.drawable.exercise),
            Habit("Reading", R.drawable.reading),
            Habit("Journaling", R.drawable.journaling),
            Habit("Prayer", R.drawable.prayer),
            Habit("Drinking water", R.drawable.drinking_water),
            Habit("Sleeping Schedule", R.drawable.sleeping_schedule)
        )

        val adapter = HabitAdapter(habitList) { habit ->
            if (habit.isSelected) selectedHabits.add(habit)
            else selectedHabits.remove(habit)
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        nextButton.setOnClickListener {
            if (selectedHabits.isEmpty()) {
                Toast.makeText(this, "Please select at least one habit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            startHabitChain(0)
        }

        fab.setOnClickListener {
            startActivity(Intent(this, NewHabitActivity::class.java))
        }
    }

    private fun startHabitChain(index: Int) {
        if (index >= selectedHabits.size) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
            return
        }

        val currentHabit = selectedHabits[index]
        val intent = when (currentHabit.title) {
            "Reading" -> Intent(this, ReadingInitActivity::class.java)
            "Drinking water" -> Intent(this, DrinkingWaterActivity::class.java)
            "Sleeping Schedule" -> Intent(this, SleepingScheduleActivity::class.java)
            else -> null
        }

        intent?.putExtra("NEXT_INDEX", index + 1)
        intent?.putExtra("HABIT_LIST", ArrayList(selectedHabits.map { it.title }))

        if (intent != null) {
            startActivity(intent)
            finish()
        } else {
            startHabitChain(index + 1)
        }
    }
}
