package com.example.domain.usecase.note

import com.example.domain.repository.NoteRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke() = repository.getAllNotes()

}