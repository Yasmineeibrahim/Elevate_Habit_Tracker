package com.elevate.utils

import Habit
import android.app.Activity
import android.content.Intent
import com.elevate.DrinkingWaterActivity
import com.elevate.ExerciseActivity
import com.elevate.JournalingActivity
import com.elevate.PrayerActivity
import com.elevate.ReadingInitActivity
import com.elevate.SleepingScheduleActivity
import com.elevate.WelcomeActivity

object Util {

    fun Activity.onNavigationToNextActivity(
        nextIndex: Int,
        selectedHabits: ArrayList<Habit>
    ) {
        if (nextIndex != -1 && nextIndex < selectedHabits.size) {
            val nextHabit = selectedHabits[nextIndex]
            val intent = when (nextHabit.title) {
                "Exercise" -> Intent(this, ExerciseActivity::class.java)
                "Reading" -> Intent(this, ReadingInitActivity::class.java)
                "Journaling" -> Intent(this, JournalingActivity::class.java)
                "Prayer" -> Intent(this, PrayerActivity::class.java)
                "Drinking water" -> Intent(this, DrinkingWaterActivity::class.java)
                "Sleeping Schedule" -> Intent(this, SleepingScheduleActivity::class.java)
                else -> Intent(this, WelcomeActivity::class.java)
            }

            intent.putParcelableArrayListExtra("SELECTED_HABITS", selectedHabits)
            intent.putExtra("NEXT_INDEX", nextIndex + 1)
            this.startActivity(intent)
        } else {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}