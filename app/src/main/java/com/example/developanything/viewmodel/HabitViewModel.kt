package com.example.developanything.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HabitViewModel : ViewModel() {
    var clickAdd by mutableStateOf(false)

    fun setAddClick() {
        clickAdd = true
    }
}