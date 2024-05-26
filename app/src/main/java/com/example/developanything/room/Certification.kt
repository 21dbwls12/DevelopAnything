package com.example.developanything.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "certification",
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
        )
    ]
)
data class Certification(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val habitId: Int,
    val date: Date,
    val image: String?,
    val voice: String?,
)
