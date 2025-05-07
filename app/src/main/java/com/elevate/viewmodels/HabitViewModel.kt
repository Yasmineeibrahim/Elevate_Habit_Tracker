package com.elevate.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.elevate.data.HabitEntity
import com.elevate.data.HabitRepository
import com.elevate.data.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.elevate.utils.SharedPreferencesHelper
import java.time.LocalDate

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HabitRepository
    private val userId: String
    private val preferences = SharedPreferencesHelper(application)

    init {
        val database = AppDatabase.getDatabase(application)
        repository = HabitRepository(database.habitDao())
        userId = application.getSharedPreferences("user_prefs", 0)
            .getString("user_id", "") ?: ""
        // Initialize star count
        viewModelScope.launch {
            calculateAndUpdateTotalStars()
        }
    }
    //---------------
    fun markHabitAsDone(habitId:Long){
        viewModelScope.launch {
            val habit=repository.getHabitById(habitId)
            if (habit!=null&&habit.currentCount<habit.practiceTimes){
                repository.updateHabit(
                    habit.copy(
                        currentCount = habit.currentCount+1
                    )
                )
            }
        }
    }
    //----------------
    fun resetDailyCountsIfNeeded(){
        viewModelScope.launch {
            val today=LocalDate.now().toString()
            val prefs=getApplication<Application>()
                .getSharedPreferences("HabitPrefs",Context.MODE_PRIVATE)
            val lastResetDate=prefs.getString("lastResetDate","")
            if (lastResetDate!=today){
                repository.resetAllHabitsCounts()
                prefs.edit().putString("lastResetDate",today).apply()
            }
        }
    }
    private suspend fun calculateAndUpdateTotalStars() {
        repository.getHabitsForUser(userId).collect { habits ->
            // Calculate stars from completed habits (5 stars per completed habit)
            val completedHabitsStars = habits.count { it.isCompleted } * 5
            preferences.setCompletedHabitsStars(completedHabitsStars)
            
            // Update total stars count
            val totalStars = preferences.getStarsCount()
            preferences.setStarsCount(totalStars)
        }
    }

    fun getHabits(): Flow<List<HabitEntity>> {
        return repository.getHabitsForUser(userId)
    }

    fun getActiveHabits(): Flow<List<HabitEntity>> {
        return repository.getActiveHabits(userId)
    }

    fun saveHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.insertHabit(habit)
            calculateAndUpdateTotalStars()
        }
    }

    fun saveHabits(habits: List<HabitEntity>) {
        viewModelScope.launch {
            repository.insertHabits(habits)
            calculateAndUpdateTotalStars()
        }
    }

    fun updateHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.updateHabit(habit)
            calculateAndUpdateTotalStars()
        }
    }

    fun completeHabit(habit: HabitEntity) {
        viewModelScope.launch {
            val updatedHabit = habit.copy(isCompleted = true)
            repository.updateHabit(updatedHabit)
            calculateAndUpdateTotalStars()
        }
    }

    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
            calculateAndUpdateTotalStars()
        }
    }

    companion object {
        fun provideFactory(
            application: Application
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return HabitViewModel(application) as T
            }
        }
    }
} 