package com.example.domain.usecase.note

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) = repository.updateNote(note = note)

}