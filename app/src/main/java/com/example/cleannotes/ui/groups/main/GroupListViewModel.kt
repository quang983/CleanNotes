package com.example.cleannotes.ui.groups.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.base.BaseViewModel
import com.example.cleannotes.event.Event
import com.example.cleannotes.event.LoadAllGroups
import com.example.domain.state.EmptyGroupsState
import com.example.domain.state.OnErrorGroupState
import com.example.domain.state.OnSuccessLoadGroupListState
import com.example.domain.usecase.group.LoadGroupsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupListViewModel @ViewModelInject constructor(
    private val loadGroupsUseCase: LoadGroupsUseCase
): BaseViewModel() {

    override fun obtainEvent(event: Event) {
        when (event) {
            LoadAllGroups -> onLoadAllGroupsEvent()
        }
    }

    private fun onLoadAllGroupsEvent() {
        viewModelScope.launch {
            try {
                val groups = withContext(Dispatchers.IO) {
                    loadGroupsUseCase()
                }
                if (groups.isEmpty()) {
                    _state.value = EmptyGroupsState
                } else {
                    _state.value = OnSuccessLoadGroupListState(data = groups)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = OnErrorGroupState(message = "Error occurred while loading groups!")
            }
        }
    }
}