package com.example.data.di

import com.example.data.repository.AppNoteRepository
import com.example.domain.repository.NoteRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<NoteRepository> { AppNoteRepository(get(), get()) }
}