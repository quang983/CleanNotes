package com.example.domain.state

import com.example.domain.model.Group
import com.example.domain.model.Note

sealed class GroupState: State

object EmptyGroupsState: GroupState()
object OnSuccessGroupActionState: GroupState()
data class OnSuccessLoadGroupListState(val data: List<Group>): GroupState()
data class OnSuccessLoadGroupState(val data: List<Note>): GroupState()
data class OnErrorGroupState(val message: String): GroupState()