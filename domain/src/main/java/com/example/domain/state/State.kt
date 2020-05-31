package com.example.domain.state

sealed class State

object OnLoadingState: State()
object OnEmptyDataState: State()
object OnSuccessActionState: State()
data class OnSuccessState<T>(val data: T): State()
data class OnErrorState(val message: String): State()