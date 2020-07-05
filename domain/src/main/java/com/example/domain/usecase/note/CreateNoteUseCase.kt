package com.example.domain.usecase.note

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(title: String, description: String, groupId: Long) {
        val note = Note(title = title, description = description, groupId = groupId)
        repository.createNewNote(note = note)
    }

}