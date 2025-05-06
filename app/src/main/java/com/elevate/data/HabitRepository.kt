package com.elevate.data

import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {
    fun getHabitsForUser(userId: String): Flow<List<HabitEntity>> {
        return habitDao.getHabitsForUser(userId)
    }

    fun getActiveHabits(userId: String): Flow<List<HabitEntity>> {
        return habitDao.getActiveHabits(userId)
    }

    suspend fun insertHabit(habit: HabitEntity) {
        habitDao.insertHabit(habit)
    }

    suspend fun insertHabits(habits: List<HabitEntity>) {
        habitDao.insertHabits(habits)
    }

    suspend fun updateHabit(habit: HabitEntity) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: HabitEntity) {
        habitDao.deleteHabit(habit)
    }

    suspend fun deleteAllHabitsForUser(userId: String) {
        habitDao.deleteAllHabitsForUser(userId)
    }
} 