package com.example.developanything.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getALLHabit(): Flow<List<Habit>>

    @Query("SELECT * FROM certification")
    fun getALLCertification(): Flow<List<Certification>>

    @Insert
    fun insertAllHabit(vararg habits: Habit)

    @Insert
    fun insertAllCertification(vararg certifications: Certification)

    @Update
    fun updateHabit(vararg habits: Habit)

    @Update
    fun updateCertification(vararg certifications: Certification)

    @Delete
    fun deleteHabit(habit: Habit)

    @Delete
    fun deleteCertification(certification: Certification)
}