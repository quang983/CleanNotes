package com.example.cleannotes.event

import com.example.domain.model.Note

sealed class NoteEvent: Event

object LoadAllNotes: NoteEvent()
object DeleteAllNotes: NoteEvent()
data class GetNoteById(val id: Long): NoteEvent()
data class CreateNewNote(val title: String, val description: String, val group: Long): NoteEvent()
data class UpdateNote(val note: Note): NoteEvent()
data class DeleteNote(val note: Note): NoteEvent()