package com.example.local.di

import com.example.local.source.LocalNoteSourceImpl
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val localSourceModule = module {
    factory<LocalNoteSourceImpl>()
}