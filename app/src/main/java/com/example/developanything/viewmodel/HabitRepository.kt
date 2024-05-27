package com.example.developanything.viewmodel

import androidx.annotation.WorkerThread
import com.example.developanything.room.Habit
import com.example.developanything.room.HabitDao
import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {
    val allHabits: Flow<List<Habit>> = habitDao.getALLHabit()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertHabit(newHabit: Habit) {
        habitDao.insertHabit(newHabit)
    }
}