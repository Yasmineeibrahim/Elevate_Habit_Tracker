package com.elevate.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val achievementsPreferences: SharedPreferences = context.getSharedPreferences("achievements", Context.MODE_PRIVATE)

    fun setUserId(userId: String) {
        sharedPreferences.edit().putString("user_id", userId).apply()
    }

    fun getUserId(): String {
        return sharedPreferences.getString("user_id", "") ?: ""
    }

    fun getUserName(): String {
        val firstName = sharedPreferences.getString("user_first_name", "") ?: ""
        val lastName = sharedPreferences.getString("user_last_name", "") ?: ""
        return if (firstName.isNotEmpty() || lastName.isNotEmpty()) {
            "$firstName $lastName".trim()
        } else {
            "User"
        }
    }

    fun setUserName(name: String) {
        val nameParts = name.split(" ")
        val firstName = nameParts.firstOrNull() ?: ""
        val lastName = nameParts.drop(1).joinToString(" ")
        
        sharedPreferences.edit()
            .putString("user_first_name", firstName)
            .putString("user_last_name", lastName)
            .apply()
    }

    fun getSelectedLanguage(): String {
        return sharedPreferences.getString("selected_language", "English") ?: "English"
    }

    fun setSelectedLanguage(language: String) {
        sharedPreferences.edit().putString("selected_language", language).apply()
    }

    fun getCurrentStreak(): Int {
        return sharedPreferences.getInt("current_streak", 0)
    }

    fun setCurrentStreak(streak: Int) {
        sharedPreferences.edit().putInt("current_streak", streak).apply()
    }

    fun getStarsCount(): Int {
        // Get stars from achievements
        val achievementsStars = achievementsPreferences.getInt("stars_count", 0)
        
        // Get stars from completed habits
        val completedHabitsStars = sharedPreferences.getInt("completed_habits_stars", 0)
        
        // Calculate total stars
        val totalStars = achievementsStars + completedHabitsStars
        
        // Update both preferences to keep them in sync
        achievementsPreferences.edit().putInt("stars_count", totalStars).apply()
        sharedPreferences.edit().putInt("total_stars", totalStars).apply()
        
        return totalStars
    }

    fun setStarsCount(count: Int) {
        sharedPreferences.edit().putInt("total_stars", count).apply()
        achievementsPreferences.edit().putInt("stars_count", count).apply()
    }

    fun setCompletedHabitsStars(count: Int) {
        sharedPreferences.edit().putInt("completed_habits_stars", count).apply()
    }

    fun getNotificationsEnabled(): Boolean {
        return sharedPreferences.getBoolean("notifications_enabled", true)
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("notifications_enabled", enabled).apply()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
        achievementsPreferences.edit().clear().apply()
    }
} 