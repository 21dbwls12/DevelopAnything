package com.example.developanything.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    // 저장된 데이터 구별을 위해 기본키 자동으로 생성되도록 설정
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    // 할 일
    @ColumnInfo(name = "todo") val todo: String,
    // 완료했는지 안했는지 확인
    @ColumnInfo(name = "isFinished") var isFinished: Boolean? = false,
    // 날짜별로 저장
    @ColumnInfo(name = "date") val date: String,
)
