package com.example.cleannotes.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleannotes.event.Event
import com.example.domain.state.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel: ViewModel() {

    protected val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State>
        get() = _state

    abstract fun obtainEvent(event: Event)

}