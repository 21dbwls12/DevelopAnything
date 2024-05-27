package com.example.developanything.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val habit: String,
    val detail: String?,
    val type: String,
)
