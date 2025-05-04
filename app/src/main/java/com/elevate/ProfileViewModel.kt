package com.elevate

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.elevate.model.User
import com.google.firebase.messaging.FirebaseMessaging

class ProfileViewModel(context: Context) : ViewModel() {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    var notificationsEnabled by mutableStateOf(prefs.getBoolean("notifications_enabled", true))
        private set

    var userProfile by mutableStateOf<User?>(null)
        private set

    private val firestoreService = FirestoreService()

    fun getUserProfile(userId: String) {
        firestoreService.getUser(userId) { user ->
            userProfile = user
        }
    }

    fun updateUserProfile(userId: String, user: User) {
        firestoreService.updateUser(userId, user)
    }

    fun toggleNotifications(enabled: Boolean) {
        notificationsEnabled = enabled
        prefs.edit().putBoolean("notifications_enabled", enabled).apply()

        if (enabled) {
            FirebaseMessaging.getInstance().subscribeToTopic("general")
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("general")
        }
    }
}
