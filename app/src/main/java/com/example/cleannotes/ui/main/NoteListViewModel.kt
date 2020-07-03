package com.example.cleannotes.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.base.BaseViewModel
import com.example.cleannotes.event.DeleteAllNotes
import com.example.cleannotes.event.Event
import com.example.cleannotes.event.LoadAllNotes
import com.example.domain.state.*
import com.example.domain.usecase.ClearNotesUseCase
import com.example.domain.usecase.GetAllNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteListViewModel @ViewModelInject constructor(
    private val loadAllNotesUseCase: GetAllNotesUseCase,
    private val clearNotesUseCase: ClearNotesUseCase
): BaseViewModel() {

    override fun obtainEvent(event: Event) {
        when (event) {
            LoadAllNotes -> onLoadAllNotesEvent()
            DeleteAllNotes -> onDeleteNotesEvent()
        }
    }

    private fun onDeleteNotesEvent() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    clearNotesUseCase()
                }
                _state.value = OnSuccessActionState
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorState(message = "Can't clear notes!")
            }
        }
    }

    private fun onLoadAllNotesEvent() {
        viewModelScope.launch {
            _state.value = OnLoadingState
            try {
                val notes = withContext(Dispatchers.IO) {
                    loadAllNotesUseCase()
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