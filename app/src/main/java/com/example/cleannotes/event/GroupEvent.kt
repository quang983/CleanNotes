package com.example.cleannotes.event

import com.example.domain.model.Group

sealed class GroupEvent: Event

object LoadAllGroups: GroupEvent()
data class LoadGroupById(val id: Long): GroupEvent()
data class CreateNewGroup(val group: Group): GroupEvent()
data class UpdateGroup(val group: Group): GroupEvent()
data class DeleteGroup(val groupId: Long): GroupEvent()