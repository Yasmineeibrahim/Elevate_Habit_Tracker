package com.elevate.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits WHERE userId = :userId")
    fun getHabitsForUser(userId: String): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE userId = :userId AND isActive = 1")
    fun getActiveHabits(userId: String): Flow<List<HabitEntity>>
    //-------
    @Query("SELECT * FROM habits WHERE id=:habitId")
    suspend fun getHabitById(habitId:Long):HabitEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabits(habits: List<HabitEntity>)

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("DELETE FROM habits WHERE userId = :userId")
    suspend fun deleteAllHabitsForUser(userId: String)

    //------------
    @Query("UPDATE habits SET currentCount=0 WHERE isActive=1")
    suspend fun resetAllCounts()
} 