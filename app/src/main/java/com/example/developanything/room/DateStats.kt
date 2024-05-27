package com.example.developanything.room

import android.icu.text.SimpleDateFormat
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.Locale

@Entity(tableName = "date_stats")
data class DateStats(
    @PrimaryKey(autoGenerate = true) val date: Date,
    val total: Int,
    val certified: Int,
) {
    val formattedDate: String
        get() {
            val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            return formatter.format(date)
        }
}
