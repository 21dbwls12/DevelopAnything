package com.example.developanything.viewmodel

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.developanything.room.AppDatabase
import com.example.developanything.room.Certification
import com.example.developanything.room.Habit
import com.example.developanything.room.HabitDao

class HabitViewModel(private val habitDao: HabitDao) : ViewModel() {
    var clickAdd by mutableStateOf(false)

    fun setAddClick() {
        clickAdd = true
    }

    @Composable
    fun getHabitList(): State<List<Habit>> {
        return habitDao.getALLHabit().collectAsState(initial = emptyList())
    }

    fun insertHabit(newHabit: Habit) {
        habitDao.insertAllHabit(newHabit)
    }

    @Composable
    fun getCertificationList(): State<List<Certification>> {
        return habitDao.getALLCertification().collectAsState(initial = emptyList())
    }
}

class HabitViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            val db = AppDatabase.getDatabase(context)
            val habitDao = db.habitDao()
            @Suppress("UNCHECKED_CAST")
            return HabitViewModel(habitDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}