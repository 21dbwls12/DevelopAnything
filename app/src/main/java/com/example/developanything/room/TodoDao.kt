package com.example.developanything.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<Todo>>

    @Insert
    fun insertAll(vararg users: Todo)

    @Update
    suspend fun updateUsers(users: Todo)

    @Delete
    fun delete(user: Todo)
}