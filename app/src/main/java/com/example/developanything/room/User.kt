package com.example.developanything.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    // 할 일
    @ColumnInfo(name = "todo") val todo: String?,
    // 완료했는지 안했는지 확인
    @ColumnInfo(name = "isFinished") val isFinished: Boolean,
    // 날짜별로 저장하기
    @ColumnInfo(name = "date") val date: Int,
)
