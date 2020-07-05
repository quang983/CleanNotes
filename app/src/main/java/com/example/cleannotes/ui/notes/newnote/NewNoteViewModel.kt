package com.example.cleannotes.ui.notes.newnote

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.base.BaseViewModel
import com.example.cleannotes.event.CreateNewNote
import com.example.cleannotes.event.Event
import com.example.cleannotes.event.LoadAllGroups
import com.example.domain.state.OnErrorNoteState
import com.example.domain.state.OnSuccessLoadGroupListState
import com.example.domain.state.OnSuccessNoteActionState
import com.example.domain.usecase.group.LoadGroupsUseCase
import com.example.domain.usecase.note.CreateNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewNoteViewModel @ViewModelInject constructor(
    private val newNoteUseCase: CreateNoteUseCase,
    private val loadAllGroupsUseCase: LoadGroupsUseCase
): BaseViewModel() {

    override fun obtainEvent(event: Event) {
        when (event) {
            is CreateNewNote -> onCreateNoteEvent(
                noteTitle = event.title,
                noteDescription = event.description,
                noteGroup = event.group
            )
            LoadAllGroups -> onLoadAllGroups()
        }
    }

    private fun onLoadAllGroups() {
        viewModelScope.launch {
            try {
                val groups = withContext(Dispatchers.IO) {
                    loadAllGroupsUseCase()
                }
                _state.value = OnSuccessLoadGroupListState(data = groups)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun onCreateNoteEvent(
        noteTitle: String,
        noteDescription: String,
        noteGroup: Long
    ) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    newNoteUseCase(
                        title = noteTitle,
                        description = noteDescription,
                        groupId = noteGroup
                    )
                }
                _state.value = OnSuccessNoteActionState
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorNoteState(message = "Error occurred while creating note!")
            }
        }
    }
}