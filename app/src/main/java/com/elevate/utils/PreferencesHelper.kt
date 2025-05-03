package com.elevate

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

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
        return prefs.getString("selected_language", "Arabic") ?: "Arabic"
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
