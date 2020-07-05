package com.example.domain.usecase.note

import com.example.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Long) = repository.getNoteById(id = id)

}