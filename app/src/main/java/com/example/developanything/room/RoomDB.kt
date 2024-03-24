package com.example.developanything.room

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow

// Room 데이터베이스를 사용하기 위한 class
// 현재 파일에서 함수들로 다 구분되어있고 파일도 2개라 db를 scope마다 계속 선언해주는 것보다 class에서 관리하는 게 더 나을 것 같다고 생각되어 class 선언
// 아니면 한 파일에 있는 함수들을 다 한 scope안에 넣고 db선언을 2번 하는게 나은가...?
// 근데 이것도 결국 class 선언하는 거랑 똑같지 않나? 함수 하나에 다 묶어도 원래처럼 Layout.kt에 있는 함수를 Screen.kt에서 사용할 수 있나? 나중에 확인해보기!
// 어쩌면 context를 계속 다른 파일에서 넘겨받아야하니깐 class로 묶는 것보다 db 2번 선언해주는 게 나을지도... 메서드도 한번씩밖에 안쓰고... 일단 계속 해보는데 잘 모르겠다..
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

    fun insertTodo(newTodo: Todo) {
        todoDao.insertAll(newTodo)
    }
    fun getTodo(): Flow<List<Todo>> {
        return todoDao.getAll()
    }
    fun deleteTodo(selectedTodo: Todo) {
        todoDao.delete(selectedTodo)
    }
    fun updateTodo(selectedTodo: Todo) {
        todoDao.updateUsers(selectedTodo)
    }
}