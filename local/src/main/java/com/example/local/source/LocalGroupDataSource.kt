package com.example.local.source

import com.example.data.entity.GroupModel
import com.example.data.entity.NoteModel
import com.example.data.source.LocalGroupSource
import com.example.local.database.dao.LocalGroupDao
import com.example.local.mapper.LocalGroupMapper
import com.example.local.mapper.LocalNoteMapper
import javax.inject.Inject

class LocalGroupDataSource @Inject constructor(
    private val dao: LocalGroupDao,
    private val groupMapper: LocalGroupMapper,
    private val noteMapper: LocalNoteMapper
): LocalGroupSource {

    override suspend fun obtainAll(): List<GroupModel> {
        return dao.obtainAllLocalGroups().map { local ->
            groupMapper.toModel(local = local)
        }
    }

    override suspend fun obtainGroup(groupId: Long): List<NoteModel> {
        val notes = dao.obtainLocalGroup(groupId = groupId).notes
        return notes.map { local ->
            noteMapper.toModel(local = local)
        }
    }

    override suspend fun create(entity: GroupModel) {
        val local = groupMapper.toLocal(entity = entity)
        dao.insertLocalGroup(localGroup = local)
    }

    override suspend fun update(entity: GroupModel) {
        val local = groupMapper.toLocal(entity = entity)
        dao.updateLocalGroup(localGroup = local)
    }

    override suspend fun delete(groupId: Long) {
        dao.deleteLocalGroup(groupId = groupId)
    }
}