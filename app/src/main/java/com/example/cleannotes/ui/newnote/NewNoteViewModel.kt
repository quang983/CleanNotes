package com.example.cleannotes.ui.newnote

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.base.BaseViewModel
import com.example.cleannotes.event.CreateNewNote
import com.example.cleannotes.event.Event
import com.example.domain.state.OnErrorState
import com.example.domain.state.OnSuccessActionState
import com.example.domain.usecase.CreateNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewNoteViewModel @ViewModelInject constructor(
    private val newNoteUseCase: CreateNoteUseCase
): BaseViewModel() {

    override fun obtainEvent(event: Event) {
        when (event) {
            is CreateNewNote -> onCreateNoteEvent(
                noteTitle = event.title, noteDescription = event.description
            )
        }
    }

    private fun onCreateNoteEvent(noteTitle: String, noteDescription: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    newNoteUseCase(
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
}