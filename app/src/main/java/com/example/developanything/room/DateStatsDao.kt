package com.example.developanything.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface DateStatsDao {
    @Query("SELECT * FROM date_stats")
    fun getDateStats(): Flow<DateStats>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDateStats(dateStats: DateStats)
}