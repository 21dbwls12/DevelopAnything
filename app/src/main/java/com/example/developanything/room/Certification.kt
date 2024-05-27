package com.example.developanything.room

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.logging.SimpleFormatter

@Entity(
    tableName = "certification",
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["uid"],
            childColumns = ["habitId"],
        )
    ]
)
data class Certification(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val habitId: Int,
    val date: Date,
    val habit: String,
    val detail: String?,
    val image: String?,
    val voice: String?,
) {
    val formattedDate: String
        get() {
            val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            return formatter.format(date)
        }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}