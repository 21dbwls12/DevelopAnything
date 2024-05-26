package com.example.developanything.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Habit::class, Certification::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao() : HabitDao
}