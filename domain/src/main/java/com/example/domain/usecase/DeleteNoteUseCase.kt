package com.example.domain.usecase

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    suspend fun execute(note: Note) = repository.deleteNote(note = note)

}