package com.example.domain.state

import com.example.domain.model.Note

sealed class NoteState: State

object EmptyNotesState: NoteState()
object OnSuccessNoteActionState: NoteState()
data class OnSuccessLoadNoteListState(val data: List<Note>): NoteState()
data class OnSuccessLoadNoteState(val note: Note): NoteState()
data class OnErrorNoteState(val message: String): NoteState()