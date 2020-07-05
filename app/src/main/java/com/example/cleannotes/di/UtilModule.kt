package com.example.cleannotes.di

import com.example.cleannotes.util.validators.AppGroupValidator
import com.example.cleannotes.util.validators.AppNoteValidator
import com.example.cleannotes.util.interfaces.GroupValidator
import com.example.cleannotes.util.interfaces.NoteValidator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class UtilModule {

    @Binds
    abstract fun bindNoteValidator(noteValidator: AppNoteValidator): NoteValidator

    @Binds
    abstract fun bindGroupValidator(groupValidator: AppGroupValidator): GroupValidator

}