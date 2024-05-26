package com.example.developanything.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "date_stats")
data class DateStats(
    @PrimaryKey val date: Date,
    val total: Int,
    val certified: Int,
)
