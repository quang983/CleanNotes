package com.example.cleannotes.di

import com.example.cleannotes.util.NoteValidator
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val utilModule = module {
    factory<NoteValidator>()
}