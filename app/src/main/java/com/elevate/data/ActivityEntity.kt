package com.elevate.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val activityName: String,
    val activityDescription: String,
    val activityIcon: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) 