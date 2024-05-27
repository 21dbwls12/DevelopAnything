package com.example.developanything.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getAllHabit(): Flow<List<Habit>>

    // paging 라이브러리 이용하여 무한 스크롤
//    @Query("SELECT * FROM habit")
//    fun getInfiniteHabit() : PagingSource<Int, Habit>

    @Query("SELECT * FROM certification")
    fun getAllCertification(): Flow<List<Certification>>

    @Insert
    fun insertHabit(vararg habits: Habit)

    @Insert
    fun insertCertification(vararg certifications: Certification)

    @Update
    fun updateHabit(vararg habits: Habit)

    @Update
    fun updateCertification(vararg certifications: Certification)

    @Delete
    fun deleteHabit(habit: Habit)

    @Delete
    fun deleteCertification(certification: Certification)
}