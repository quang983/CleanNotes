package com.example.cleannotes.ui.groups.create

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.base.BaseViewModel
import com.example.cleannotes.event.CreateNewGroup
import com.example.cleannotes.event.Event
import com.example.domain.model.Group
import com.example.domain.state.OnErrorGroupState
import com.example.domain.state.OnSuccessGroupActionState
import com.example.domain.usecase.group.CreateGroupUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateGroupViewModel @ViewModelInject constructor(
    private val createGroupUseCase: CreateGroupUseCase
): BaseViewModel() {

    override fun obtainEvent(event: Event) {
        when (event) {
            is CreateNewGroup -> onCreateGroup(group = event.group)
        }
    }

    private fun onCreateGroup(group: Group) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    createGroupUseCase(group = group)
                }
                _state.value = OnSuccessGroupActionState
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorGroupState(message = "Error occurred while creating group!")
            }
        }
    }
}