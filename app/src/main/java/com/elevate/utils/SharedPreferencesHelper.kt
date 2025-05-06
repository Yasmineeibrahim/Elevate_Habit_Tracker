package com.elevate.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

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
        return sharedPreferences.getInt("total_stars", 0)
    }

    fun setStarsCount(count: Int) {
        sharedPreferences.edit().putInt("total_stars", count).apply()
    }

    fun getNotificationsEnabled(): Boolean {
        return sharedPreferences.getBoolean("notifications_enabled", true)
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("notifications_enabled", enabled).apply()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
} 