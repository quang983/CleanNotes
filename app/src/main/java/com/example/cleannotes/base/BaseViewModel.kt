package com.example.cleannotes.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.state.State
import com.example.cleannotes.event.Event

abstract class BaseViewModel: ViewModel() {

    protected val _state: MutableLiveData<State> = MutableLiveData()

    abstract fun obtainEvent(event: Event)

}