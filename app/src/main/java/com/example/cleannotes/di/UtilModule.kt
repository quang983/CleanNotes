package com.example.cleannotes.di

import com.example.cleannotes.util.NoteValidator
import com.example.cleannotes.util.Validator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class UtilModule {

    @Binds
    abstract fun bindValidator(noteValidator: NoteValidator): Validator

}