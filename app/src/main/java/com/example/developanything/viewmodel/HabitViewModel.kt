package com.example.developanything.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.developanything.room.Certification
import com.example.developanything.room.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class HabitViewModel(private val repository: HabitRepository) : ViewModel() {
    val allHabit: LiveData<List<Habit>> = repository.allHabit.asLiveData()
    val allCertification: LiveData<List<Certification>> = repository.allCertification.asLiveData()
    var clickAdd by mutableStateOf(false)
    var habit by mutableStateOf("")
    var detail by mutableStateOf("")
    var clickImage by mutableStateOf(true)
    var clickVoice by mutableStateOf(false)
    var typeText by mutableStateOf("image")
    var selectedUri: Uri? by mutableStateOf(null)
    var habitId by mutableIntStateOf(0)
    var flipCard by mutableStateOf(false)


    // paging 라이브러리 이용하여 무한 스크롤
//    @Composable
//    fun infiniteHabit(): Flow<PagingData<Habit>> {
//        return repository.allInfiniteHabit
//    }

    fun cancelAddHabit() {
        habit = ""
        detail = ""
        typeText = "image"
        clickImage = true
        clickVoice = false
        clickAdd = false
    }

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
            typeText = "image"
            clickImage = true
            clickVoice = false
            clickAdd = false
        }
    }

    private fun insertCertification(certification: Certification) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCertification(certification)
        }
    }

    fun addCertification() {
        val newCertification = Certification(
            habitId = habitId,
            date = Date(System.currentTimeMillis()),
            habit = habit,
            detail = detail,
            image = if (typeText == "image") selectedUri.toString() else null,
            voice = if (typeText == "image") null else selectedUri.toString()
        )
        viewModelScope.launch(Dispatchers.IO) {
            insertCertification(newCertification)
        }
        habitId = 0
        habit = ""
        detail = ""
        typeText = "image"
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