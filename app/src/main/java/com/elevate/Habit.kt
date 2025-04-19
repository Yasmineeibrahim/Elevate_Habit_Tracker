package com.elevate

data class Habit(
    val title: String,
    val imageResId: Int,
    var isSelected: Boolean = false
)
