package com.example.cleannotes.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.base.BaseViewModel
import com.example.cleannotes.event.Event
import com.example.cleannotes.event.LoadAllNotes
import com.example.domain.state.*
import com.example.domain.usecase.GetAllNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteListViewModel(
    private val getAllUseCase: GetAllNotesUseCase
) : BaseViewModel() {

    val state: LiveData<State>
        get() = _state

    override fun obtainEvent(event: Event) {
        when(event) {
            LoadAllNotes -> onLoadListEvent()
        }
    }

    private fun onLoadListEvent() {
        _state.value = OnLoadingState
        viewModelScope.launch {
            try {
                val notes = withContext(Dispatchers.IO) {
                    getAllUseCase.execute()
                }
                if (notes.isEmpty()) {
                    _state.value = OnEmptyDataState
                } else {
                    _state.value = OnSuccessState(data = notes)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorState(message = "Error occurred while loading data!")
            }
        }
    }

}
