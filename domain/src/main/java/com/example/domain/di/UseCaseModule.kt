package com.example.domain.di

import com.example.domain.usecase.*
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val useCaseModule = module {
    factory<GetAllNotesUseCase>()
    factory<GetNoteByIdUseCase>()
    factory<CreateNoteUseCase>()
    factory<UpdateNoteUseCase>()
    factory<DeleteNoteUseCase>()
    factory<ClearNotesUseCase>()
}