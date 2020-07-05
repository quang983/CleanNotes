package com.example.cleannotes.ui.notes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.base.BaseViewModel
import com.example.cleannotes.event.*
import com.example.domain.model.Note
import com.example.domain.state.OnErrorNoteState
import com.example.domain.state.OnSuccessNoteActionState
import com.example.domain.state.OnSuccessLoadNoteState
import com.example.domain.usecase.group.LoadGroupsUseCase
import com.example.domain.usecase.note.DeleteNoteUseCase
import com.example.domain.usecase.note.GetNoteByIdUseCase
import com.example.domain.usecase.note.UpdateNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel @ViewModelInject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
): BaseViewModel() {

    override fun obtainEvent(event: Event) {
        when (event) {
            is GetNoteById -> onGetNoteByIdEvent(id = event.id)
            is UpdateNote -> onUpdateNoteEvent(note = event.note)
            is DeleteNote -> onDeleteNoteEvent(note = event.note)
        }
    }

    private fun onDeleteNoteEvent(note: Note) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    deleteNoteUseCase(note = note)
                }
                _state.value = OnSuccessNoteActionState
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorNoteState(message = "Error occurred while deleting note!")
            }
        }
    }

    private fun onUpdateNoteEvent(note: Note) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    updateNoteUseCase(note = note)
                }
                _state.value = OnSuccessNoteActionState
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorNoteState(message = "Something went wrong!")
            }
        }
    }

    private fun onGetNoteByIdEvent(id: Long) {
        viewModelScope.launch {
            try {
                val note = withContext(Dispatchers.IO) {
                    getNoteByIdUseCase(id = id)
                }
                _state.value = OnSuccessLoadNoteState(note = note)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorNoteState(message = "Error while loading note!")
            }
        }
    }
}