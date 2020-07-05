package com.example.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.local.database.dao.LocalGroupDao
import com.example.local.database.dao.LocalNoteDao
import com.example.local.entity.LocalGroup
import com.example.local.entity.LocalNote

@Database(
    entities = [LocalNote::class, LocalGroup::class],
    version = 2
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getLocalNoteDao(): LocalNoteDao
    abstract fun getLocalGroupDao(): LocalGroupDao

    companion object{
        const val DATABASE_NAME = "app_db"
    }
}