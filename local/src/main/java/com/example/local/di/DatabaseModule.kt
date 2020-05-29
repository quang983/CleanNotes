package com.example.local.di

import androidx.room.Room
import com.example.local.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    single {
        get<AppDatabase>().getLocalNoteDao()
    }

}