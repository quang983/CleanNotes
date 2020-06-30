package com.example.local.source

import com.example.data.entity.NoteModel
import com.example.data.source.LocalNoteSource
import com.example.local.database.dao.LocalNoteDao
import com.example.local.mapper.LocalNoteMapper
import javax.inject.Inject

class LocalNoteDataSource @Inject constructor(
    private val localDao: LocalNoteDao,
    private val mapper: LocalNoteMapper
): LocalNoteSource {

    override suspend fun obtainAll(): List<NoteModel> {
        return localDao.getAllLocalNotes()
            .map { local ->
                mapper.toModel(local = local)
            }
    }

    override suspend fun obtainById(id: Long): NoteModel {
        val note = localDao.getLocalNoteById(id = id)
        return mapper.toModel(local = note)
    }

    override suspend fun create(entity: NoteModel) {
        val local = mapper.toLocal(entity = entity)
        localDao.addNewLocalNote(note = local)
    }

    override suspend fun update(entity: NoteModel) {
        val local = mapper.toLocal(entity = entity)
        localDao.updateLocalNote(note = local)
    }

    override suspend fun delete(entity: NoteModel) {
        val local = mapper.toLocal(entity = entity)
        localDao.deleteLocalNote(note = local)
    }

    override suspend fun clear() {
        localDao.clearAllNotes()
    }

}