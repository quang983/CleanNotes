package com.example.domain.repository

import com.example.domain.model.Note

interface NoteRepository {

    suspend fun getAllNotes(): List<Note>
    suspend fun getNoteById(id: Long): Note
    suspend fun createNewNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)

}