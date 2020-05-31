package com.example.domain.usecase

import com.example.domain.repository.NoteRepository

class GetNoteByIdUseCase(
    private val repository: NoteRepository
) {

    suspend fun execute(id: Long) = repository.getNoteById(id = id)

}