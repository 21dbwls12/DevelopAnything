package com.example.developanything.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Habit::class, Certification::class, DateStats::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao() : HabitDao

    abstract fun dateStatsDao() : DateStatsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "ch"
                ).build()
                db
            }
        }
    }
}