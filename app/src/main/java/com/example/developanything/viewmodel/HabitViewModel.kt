package com.example.developanything.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.developanything.room.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository) : ViewModel() {
    val allHabit: LiveData<List<Habit>> = repository.allHabit.asLiveData()
    var clickAdd by mutableStateOf(false)
    var habit by mutableStateOf("")
    var detail by mutableStateOf("")
    var clickImage by mutableStateOf(true)
    var clickVoice by mutableStateOf(false)
    var typeText by mutableStateOf("image")

    // paging 라이브러리 이용하여 무한 스크롤
//    @Composable
//    fun infiniteHabit(): Flow<PagingData<Habit>> {
//        return repository.allInfiniteHabit
//    }

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