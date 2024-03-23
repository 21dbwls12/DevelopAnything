package com.example.developanything.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Todo::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao
}

// data class 이름 바꾸면서 db table 이름도 변경
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE User RENAME TO Todo")
    }
}
// uid를 nullable로 선언해줘서 non-null로 변경
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Todo_new (uid INTEGER NOT NULL, todo TEXT NOT NULL, isFinished INTEGER, date TEXT NOT NULL, PRIMARY KEY(uid))")
        db.execSQL("INSERT INTO Todo_new (uid, todo, isFinished, date) SELECT uid, todo, isFinished, date FROM todo")
        db.execSQL("DROP TABLE Todo")
        db.execSQL("ALTER TABLE Todo_new RENAME TO Todo")
    }
}
// uid를 non-null로 변경하면서 column 순서가 변경되어 다시 data class Todo의 순서에 맞춰서 변경
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Todo_new (uid INTEGER NOT NULL, todo TEXT NOT NULL, isFinished INTEGER, date TEXT NOT NULL, PRIMARY KEY(uid))")
        db.execSQL("INSERT INTO Todo_new (uid, todo, isFinished, date) SELECT uid, todo, isFinished, date FROM todo")
        db.execSQL("DROP TABLE Todo")
        db.execSQL("ALTER TABLE Todo_new RENAME TO Todo")
    }
}