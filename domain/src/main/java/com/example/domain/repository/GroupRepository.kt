package com.example.domain.repository

import com.example.domain.model.Group
import com.example.domain.model.Note

interface GroupRepository {
    suspend fun createGroup(group: Group)
    suspend fun deleteGroup(groupId: Long)
    suspend fun updateGroup(group: Group)
    suspend fun loadGroups(): List<Group>
    suspend fun loadGroup(groupId: Long): List<Note>
}