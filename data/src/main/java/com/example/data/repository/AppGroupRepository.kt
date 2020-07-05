package com.example.data.repository

import com.example.data.mapper.GroupMapper
import com.example.data.mapper.NoteMapper
import com.example.data.source.LocalGroupSource
import com.example.domain.model.Group
import com.example.domain.model.Note
import com.example.domain.repository.GroupRepository
import javax.inject.Inject

class AppGroupRepository @Inject constructor(
    private val localSource: LocalGroupSource,
    private val groupMapper: GroupMapper,
    private val noteMapper: NoteMapper
): GroupRepository {

    override suspend fun createGroup(group: Group) {
        val entity = groupMapper.toModel(entity = group)
        localSource.create(entity = entity)
    }

    override suspend fun deleteGroup(groupId: Long) {
        localSource.delete(groupId = groupId)
    }

    override suspend fun updateGroup(group: Group) {
        val entity = groupMapper.toModel(entity = group)
        localSource.update(entity = entity)
    }

    override suspend fun loadGroups(): List<Group> {
        return localSource.obtainAll().map { model ->
            groupMapper.toEntity(model = model)
        }
    }

    override suspend fun loadGroup(groupId: Long): List<Note> {
        return localSource.obtainGroup(groupId = groupId).map { model ->
            noteMapper.toEntity(model = model)
        }
    }
}