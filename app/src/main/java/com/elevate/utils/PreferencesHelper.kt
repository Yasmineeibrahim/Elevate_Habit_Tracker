package com.elevate

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class SharedPreferencesHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun setNotificationsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("notifications_enabled", enabled).apply()
    }

    fun isNotificationsEnabled(): Boolean {
        return prefs.getBoolean("notifications_enabled", true)
    }

    fun setSelectedLanguage(language: String) {
        prefs.edit().putString("selected_language", language).apply()
    }

    fun getSelectedLanguage(): String {
        return prefs.getString("selected_language", "English") ?: "English"
    }

    fun setUserName(firstName: String, lastName: String) {
        prefs.edit().putString("user_first_name", firstName).apply()
        prefs.edit().putString("user_last_name", lastName).apply()
    }

    fun getUserName(): String {
        val firstName = prefs.getString("user_first_name", "") ?: ""
        val lastName = prefs.getString("user_last_name", "") ?: ""
        return if (firstName.isNotEmpty() || lastName.isNotEmpty()) {
            "$firstName $lastName".trim()
        } else {
            "User"
        }
    }

    fun saveHabits(habits: List<HabitUiData>) {
        val habitsJson = gson.toJson(habits)
        prefs.edit().putString("user_habits", habitsJson).apply()
    }

    fun getHabits(): List<HabitUiData> {
        val habitsJson = prefs.getString("user_habits", null)
        return if (habitsJson != null) {
            val type = object : TypeToken<List<HabitUiData>>() {}.type
            gson.fromJson(habitsJson, type)
        } else {
            emptyList()
        }
    }

    fun addHabit(habit: HabitUiData) {
        val currentHabits = getHabits().toMutableList()
        currentHabits.add(habit)
        saveHabits(currentHabits)
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }

    fun updateStreak() {
        val lastOpenDate = prefs.getLong("last_open_date", 0)
        val currentStreak = prefs.getInt("current_streak", 0)
        val currentTime = System.currentTimeMillis()
        
        // If this is the first time opening the app
        if (lastOpenDate == 0L) {
            prefs.edit()
                .putLong("last_open_date", currentTime)
                .putInt("current_streak", 1)
                .apply()
            return
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = lastOpenDate
        val lastOpenDay = calendar.get(Calendar.DAY_OF_YEAR)
        val lastOpenYear = calendar.get(Calendar.YEAR)

        calendar.timeInMillis = currentTime
        val currentDay = calendar.get(Calendar.DAY_OF_YEAR)
        val currentYear = calendar.get(Calendar.YEAR)

        // Check if it's the next day
        if ((currentYear == lastOpenYear && currentDay == lastOpenDay + 1) ||
            (currentYear == lastOpenYear + 1 && currentDay == 1 && lastOpenDay == calendar.getActualMaximum(Calendar.DAY_OF_YEAR))) {
            // Consecutive day, increment streak
            prefs.edit()
                .putLong("last_open_date", currentTime)
                .putInt("current_streak", currentStreak + 1)
                .apply()
        } else if (currentDay != lastOpenDay || currentYear != lastOpenYear) {
            // Not consecutive, reset streak
            prefs.edit()
                .putLong("last_open_date", currentTime)
                .putInt("current_streak", 1)
                .apply()
        }
    }

    fun getCurrentStreak(): Int {
        return prefs.getInt("current_streak", 0)
    }
}
