package com.example.cleannotes.di

import android.content.Context
import androidx.room.Room
import com.example.local.database.AppDatabase
import com.example.local.database.dao.LocalGroupDao
import com.example.local.database.dao.LocalNoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(database: AppDatabase): LocalNoteDao = database.getLocalNoteDao()

    @Singleton
    @Provides
    fun provideGroupDao(database: AppDatabase): LocalGroupDao = database.getLocalGroupDao()
}