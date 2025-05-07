package com.elevate.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val habitName: String,
    val practiceTimes: Int,
    val startTime: String = "10:00 PM", // Default value
    val endTime: String = "10:00 PM",   // Default value
    val preferredTime: String? = null,   // Nullable preferred time
    val isCompleted: Boolean = false,    // Track if the habit is completed
    val isActive: Boolean = true,        // Default to true for new habits
    val createdAt: Long = System.currentTimeMillis(),
    val currentCount:Int=0
)