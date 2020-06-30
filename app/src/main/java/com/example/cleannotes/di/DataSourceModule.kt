package com.example.cleannotes.di

import com.example.data.source.LocalNoteSource
import com.example.local.source.LocalNoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindLocalNoteSource(dataSource: LocalNoteDataSource): LocalNoteSource

}