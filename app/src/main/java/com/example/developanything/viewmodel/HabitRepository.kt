package com.example.developanything.viewmodel

import com.example.developanything.room.Certification
import com.example.developanything.room.Habit
import com.example.developanything.room.HabitDao
import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {
    val allHabit: Flow<List<Habit>> = habitDao.getAllHabit()
    val allCertification: Flow<List<Certification>> = habitDao.getAllCertification()
    // paging 라이브러리 이용하여 무한 스크롤
//    val allInfiniteHabit: Flow<PagingData<Habit>> = Pager(PagingConfig(pageSize = 5)) {
//        habitDao.getInfiniteHabit()
//    }.flow

    fun insertHabit(newHabit: Habit) {
        habitDao.insertHabit(newHabit)
    }

    fun insertCertification(newCertification: Certification) {
        habitDao.insertCertification(newCertification)
    }
}