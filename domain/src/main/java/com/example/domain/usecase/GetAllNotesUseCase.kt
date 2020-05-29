package com.example.domain.usecase

import com.example.domain.repository.NoteRepository

class GetAllNotesUseCase(
    private val repository: NoteRepository
) {

    suspend fun execute() = repository.getAllNotes()

}