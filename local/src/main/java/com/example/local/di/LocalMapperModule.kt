package com.example.local.di

import com.example.local.mapper.LocalNoteMapper
import org.koin.dsl.module

val localMapperModule = module {
    factory { LocalNoteMapper() }
}