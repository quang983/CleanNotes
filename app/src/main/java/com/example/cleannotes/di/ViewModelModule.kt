package com.example.cleannotes.di

import com.example.cleannotes.ui.main.NoteListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ NoteListViewModel(get()) }
}