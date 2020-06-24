package com.example.domain.usecase

import com.example.domain.repository.NoteRepository

class ClearNotesUseCase(
    private val repository: NoteRepository
) {

    suspend fun execute() = repository.clearNotes()

}