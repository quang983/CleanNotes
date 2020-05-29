package com.example.data.di

import com.example.data.mapper.NoteMapper
import org.koin.dsl.module

val mapperModule = module {
    factory { NoteMapper() }
}