package com.example.domain.usecase

import com.example.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend fun execute(id: Long) = repository.getNoteById(id = id)

}