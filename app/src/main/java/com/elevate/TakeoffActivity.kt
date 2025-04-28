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
            if (habit.isSelected) {
                selectedHabits.add(habit)
            } else {
                selectedHabits.remove(habit)
            }
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        nextButton.setOnClickListener {
            Toast.makeText(this, "Selected: ${selectedHabits.map { it.title }}", Toast.LENGTH_SHORT).show()

            if (selectedHabits.any { it.title == "Reading" }) {
                startActivity(Intent(this, ReadingInitActivity::class.java))
            }

        }


        fab.setOnClickListener {
            Toast.makeText(this, "add new habit", Toast.LENGTH_SHORT).show()
        }
    }
}
