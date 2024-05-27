package com.example.developanything.viewmodel

import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.developanything.room.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository) : ViewModel() {
    val allHabits: LiveData<List<Habit>> = repository.allHabits.asLiveData()
    var clickAdd by mutableStateOf(false)
    var habit by mutableStateOf("")
    var detail by mutableStateOf("")
    var clickImage by mutableStateOf(true)
    var clickVoice by mutableStateOf(false)
    var typeText by mutableStateOf("image")

    private fun insertHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertHabit(habit)
        }
    }

    fun addHabit() {
        if (habit.isNotBlank() && typeText.isNotBlank()) {
            val newHabit = Habit(habit = habit, detail = detail, type = typeText)
            viewModelScope.launch(Dispatchers.IO) {
                insertHabit(newHabit)
            }
            habit = ""
            detail = ""
            clickAdd = false
        }
    }
}

class HabitViewModelFactory(private val repository: HabitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}