package com.example.cleannotes.di

import com.example.cleannotes.ui.AppViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<AppViewModel>()
}