package com.example.cleannotes.ui.groups.group

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.base.BaseViewModel
import com.example.cleannotes.event.DeleteGroup
import com.example.cleannotes.event.Event
import com.example.cleannotes.event.LoadGroupById
import com.example.domain.state.EmptyGroupsState
import com.example.domain.state.OnErrorGroupState
import com.example.domain.state.OnSuccessGroupActionState
import com.example.domain.state.OnSuccessLoadGroupState
import com.example.domain.usecase.group.DeleteGroupUseCase
import com.example.domain.usecase.group.LoadGroupUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupViewModel @ViewModelInject constructor(
    private val loadGroupById: LoadGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase
): BaseViewModel() {

    override fun obtainEvent(event: Event) {
        when (event) {
            is LoadGroupById -> onLoadGroupEvent(id = event.id)
            is DeleteGroup -> onDeleteGroup(id = event.groupId)
        }
    }

    private fun onDeleteGroup(id: Long) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    deleteGroupUseCase(groupId = id)
                }
                _state.value = OnSuccessGroupActionState
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorGroupState(message = "Error occurred while deleting group!")
            }
        }
    }

    private fun onLoadGroupEvent(id: Long) {
        viewModelScope.launch {
            try {
                val notes = withContext(Dispatchers.IO) {
                    loadGroupById(groupId = id)
                }
                if (notes.isEmpty()) {
                    _state.value = EmptyGroupsState
                } else {
                    _state.value = OnSuccessLoadGroupState(data = notes)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorGroupState(message = "Error occurred while loading group!")
            }
        }
    }
}