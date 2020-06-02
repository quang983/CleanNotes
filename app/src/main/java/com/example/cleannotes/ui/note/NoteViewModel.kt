package com.example.cleannotes.ui.note

import androidx.lifecycle.viewModelScope
import com.example.cleannotes.base.BaseViewModel
import com.example.cleannotes.event.DeleteNote
import com.example.cleannotes.event.Event
import com.example.cleannotes.event.GetNoteById
import com.example.cleannotes.event.UpdateNote
import com.example.domain.model.Note
import com.example.domain.state.OnErrorState
import com.example.domain.state.OnLoadingState
import com.example.domain.state.OnSuccessActionState
import com.example.domain.state.OnSuccessState
import com.example.domain.usecase.DeleteNoteUseCase
import com.example.domain.usecase.GetNoteByIdUseCase
import com.example.domain.usecase.UpdateNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) : BaseViewModel() {


    override fun obtainEvent(event: Event) {
        when (event) {
            is GetNoteById -> onGetNoteEvent(id = event.id)
            is DeleteNote -> onDeleteNoteEvent(note = event.note)
            is UpdateNote -> onUpdateNoteEvent(note = event.note)
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
                _state.value = OnErrorState(message = "Can't update note!")
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
                _state.value = OnErrorState(message = "Something went wrong :(")
            }
        }
    }

    private fun onGetNoteEvent(id: Long) {
        _state.value = OnLoadingState
        viewModelScope.launch {
            try {
                val note = withContext(Dispatchers.IO) {
                    getNoteByIdUseCase.execute(id = id)
                }
                _state.value = OnSuccessState(data = note)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorState(message = "Can't load note")
            }
        }
    }


}