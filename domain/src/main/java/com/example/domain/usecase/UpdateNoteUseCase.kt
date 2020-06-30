package com.example.domain.usecase

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend fun execute(note: Note) = repository.updateNote(note = note)

}