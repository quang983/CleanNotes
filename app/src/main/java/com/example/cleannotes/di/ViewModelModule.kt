package com.example.cleannotes.di

import com.example.cleannotes.ui.main.NoteListViewModel
import com.example.cleannotes.ui.newnote.CreateNewNoteViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<NoteListViewModel>()
    viewModel<CreateNewNoteViewModel>()
}