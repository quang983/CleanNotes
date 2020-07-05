package com.example.cleannotes.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleannotes.event.Event
import com.example.domain.state.State

abstract class BaseViewModel: ViewModel() {

    protected val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State>
        get() = _state

    abstract fun obtainEvent(event: Event)

}