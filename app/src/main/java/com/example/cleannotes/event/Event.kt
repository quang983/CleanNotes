package com.example.cleannotes.event

import com.example.domain.model.Note

sealed class Event

object LoadAllNotes: Event()
data class GetNoteById(val id: Long): Event()
data class CreateNewNote(val title: String, val description: String): Event()
data class UpdateNote(val note: Note): Event()
data class DeleteNote(val note: Note): Event()