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
        return sharedPreferences.getString("user_name", "") ?: ""
    }

    fun setUserName(name: String) {
        sharedPreferences.edit().putString("user_name", name).apply()
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
        val achievementsStars = achievementsPreferences.getInt("stars_count", 0)
        val totalStars = sharedPreferences.getInt("total_stars", 0)
        val maxStars = maxOf(achievementsStars, totalStars)
        
        // Sync both values to the maximum
        if (maxStars > achievementsStars) {
            achievementsPreferences.edit().putInt("stars_count", maxStars).apply()
        }
        if (maxStars > totalStars) {
            sharedPreferences.edit().putInt("total_stars", maxStars).apply()
        }
        
        return maxStars
    }

    fun setStarsCount(count: Int) {
        sharedPreferences.edit().putInt("total_stars", count).apply()
        achievementsPreferences.edit().putInt("stars_count", count).apply()
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