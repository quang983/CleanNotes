package com.example.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.local.database.dao.LocalNoteDao
import com.example.local.entity.LocalNote

@Database(
    entities = [LocalNote::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getLocalNoteDao(): LocalNoteDao

    companion object{
        const val DATABASE_NAME = "app_db"
    }
}