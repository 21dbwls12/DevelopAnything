package com.example.developanything.room

import android.content.Context
import androidx.room.Room

class RoomDB private constructor(context: Context) {
    // Room
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "todo.db"
    ).addMigrations(
        MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4
    ).build()

    private val todoDao = db.todoDao()

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getInstance(context: Context): RoomDB = INSTANCE ?: synchronized(this) {
            INSTANCE ?: RoomDB(context.applicationContext).also { INSTANCE = it }
        }
    }

    val todoList: List<Todo> = todoDao.getAll()

    fun insertTodo(newTodo: Todo) {
        todoDao.insertAll(newTodo)
    }
}