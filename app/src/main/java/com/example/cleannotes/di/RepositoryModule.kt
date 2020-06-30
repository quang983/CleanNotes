package com.example.cleannotes.di

import com.example.data.repository.AppNoteRepository
import com.example.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindNoteRepository(repository: AppNoteRepository): NoteRepository

}