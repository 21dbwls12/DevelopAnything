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
import androidx.room.Room
import com.example.developanything.room.AppDatabase
import com.example.developanything.room.Certification
import com.example.developanything.room.Habit

class HabitViewModel(context: Context) : ViewModel() {
    private val db = AppDatabase.getDatabase(context)

    private val habitDao = db.habitDao()

    var clickAdd by mutableStateOf(false)

    fun setAddClick() {
        clickAdd = true
    }

    @Composable
    fun getHabitList(): State<List<Habit>> {
        return habitDao.getALLHabit().collectAsState(initial = emptyList())
    }

    @Composable
    fun getCertificationList(): State<List<Certification>> {
        return habitDao.getALLCertification().collectAsState(initial = emptyList())
    }
}