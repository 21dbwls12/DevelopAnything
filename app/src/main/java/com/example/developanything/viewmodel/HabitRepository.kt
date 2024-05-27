package com.example.developanything.viewmodel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.developanything.room.Habit
import com.example.developanything.room.HabitDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count

class HabitRepository(private val habitDao: HabitDao) {
    val allHabit: Flow<List<Habit>> = habitDao.getAllHabit()
    // paging 라이브러리 이용하여 무한 스크롤
//    val allInfiniteHabit: Flow<PagingData<Habit>> = Pager(PagingConfig(pageSize = 5)) {
//        habitDao.getInfiniteHabit()
//    }.flow

    fun insertHabit(newHabit: Habit) {
        habitDao.insertHabit(newHabit)
    }
}