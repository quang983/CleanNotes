package com.example.domain.usecase

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository

class CreateNoteUseCase(
    private val repository: NoteRepository
) {

    suspend fun execute(note: Note) = repository.createNewNote(note = note)

}