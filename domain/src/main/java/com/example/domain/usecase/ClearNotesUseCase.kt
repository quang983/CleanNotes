package com.example.domain.usecase

import com.example.domain.repository.NoteRepository
import javax.inject.Inject

class ClearNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend fun execute() = repository.clearNotes()

}