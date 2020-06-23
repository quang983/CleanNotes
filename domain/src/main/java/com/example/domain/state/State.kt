package com.example.domain.state

import com.example.domain.model.Note

sealed class State

object OnLoadingState: State()
object OnEmptyDataState: State()
object OnSuccessActionState: State()
data class OnSuccessLoadListState(val data: List<Note>): State()
data class OnSuccessLoadNoteState(val note: Note): State()
data class OnErrorState(val message: String): State()