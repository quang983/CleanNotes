package com.example.data.source

import com.example.data.entity.GroupModel
import com.example.data.entity.NoteModel

interface LocalGroupSource {
    suspend fun obtainAll(): List<GroupModel>
    suspend fun obtainGroup(groupId: Long): List<NoteModel>
    suspend fun create(entity: GroupModel)
    suspend fun update(entity: GroupModel)
    suspend fun delete(groupId: Long)
}