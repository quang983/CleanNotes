package com.example.cleannotes.ui.newnote

import androidx.lifecycle.viewModelScope
import com.example.cleannotes.base.BaseViewModel
import com.example.cleannotes.event.CreateNewNote
import com.example.cleannotes.event.Event
import com.example.domain.model.Note
import com.example.domain.state.OnErrorState
import com.example.domain.state.OnSuccessActionState
import com.example.domain.usecase.CreateNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateNewNoteViewModel(
    private val createNewNoteUseCase: CreateNoteUseCase
): BaseViewModel() {

    override fun obtainEvent(event: Event) {
        when (event) {
            is CreateNewNote -> onCreateNewNote(title = event.title, description = event.description)
        }
    }

    private fun onCreateNewNote(title: String, description: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    createNewNoteUseCase.execute(title = title, description = description)
                }
                _state.value = OnSuccessActionState
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorState(message = "Can't create note!")
            }
        }
    }
}
