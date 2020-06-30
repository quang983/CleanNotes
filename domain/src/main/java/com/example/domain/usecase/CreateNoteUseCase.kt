package com.example.domain.usecase

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend fun execute(title: String, description: String) {
        val note = Note(title = title, description = description)
        repository.createNewNote(note = note)
    }

}