package com.example.cleannotes.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.base.BaseViewModel
import com.example.cleannotes.event.*
import com.example.domain.model.Note
import com.example.domain.state.*
import com.example.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppViewModel @ViewModelInject constructor(
    private val createNoteUseCase: CreateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val clearNotesUseCase: ClearNotesUseCase
): BaseViewModel() {

    override fun obtainEvent(event: Event) {
        when (event) {
            LoadAllNotes -> onLoadAllNotesEvent()
            is GetNoteById -> onGetNoteByIdEvent(id = event.id)
            is CreateNewNote -> onCreateNewNoteEvent(
                noteTitle = event.title, noteDescription = event.description
            )
            is UpdateNote -> onUpdateNoteEvent(note = event.note)
            is DeleteNote -> onDeleteNoteEvent(note = event.note)
            DeleteAllNotes -> onClearAllNotesEvent()
        }
    }

    private fun onClearAllNotesEvent() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    clearNotesUseCase.execute()
                }
                _state.value = OnSuccessActionState
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorState(message = "")
            }
        }
    }

    private fun onDeleteNoteEvent(note: Note) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    deleteNoteUseCase.execute(note = note)
                }
                _state.value = OnSuccessActionState
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorState(message = "Error occurred while deleting note!")
            }
        }
    }

    private fun onUpdateNoteEvent(note: Note) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    updateNoteUseCase.execute(note = note)
                }
                _state.value = OnSuccessActionState
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorState(message = "Something went wrong!")
            }
        }
    }

    private fun onCreateNewNoteEvent(noteTitle: String, noteDescription: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    createNoteUseCase.execute(
                        title = noteTitle, description = noteDescription
                    )
                }
                _state.value = OnSuccessActionState
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorState(message = "Error occurred while creating note!")
            }
        }
    }

    private fun onGetNoteByIdEvent(id: Long) {
        viewModelScope.launch {
            try {
                val note = withContext(Dispatchers.IO) {
                    getNoteByIdUseCase.execute(id = id)
                }
                _state.value = OnSuccessLoadNoteState(note = note)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorState(message = "Error while loading note!")
            }
        }
    }

    private fun onLoadAllNotesEvent() {
        viewModelScope.launch {
            _state.value = OnLoadingState
            try {
                val notes = withContext(Dispatchers.IO) {
                    getAllNotesUseCase.execute()
                }
                if (notes.isEmpty()) {
                    _state.value = OnEmptyDataState
                } else {
                    _state.value = OnSuccessLoadListState(data = notes)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorState(message = "Can't load notes!")
            }
        }
    }

}