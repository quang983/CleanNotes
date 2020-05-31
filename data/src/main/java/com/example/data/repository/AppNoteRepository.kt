package com.example.data.repository

import com.example.data.mapper.NoteMapper
import com.example.data.source.LocalNoteSource
import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository

class AppNoteRepository(
    private val localDataSource: LocalNoteSource,
    private val mapper: NoteMapper
): NoteRepository {

    override suspend fun getAllNotes(): List<Note> {
        return localDataSource.obtainAll()
            .map { model ->
                mapper.toEntity(model = model)
            }
    }

    override suspend fun getNoteById(id: Long): Note {
        val model = localDataSource.obtainById(id = id)
        return mapper.toEntity(model = model)
    }

    override suspend fun createNewNote(note: Note) {
        val entity = mapper.toModel(entity = note)
        localDataSource.create(entity = entity)
    }

    override suspend fun updateNote(note: Note) {
        val entity = mapper.toModel(entity = note)
        localDataSource.update(entity = entity)
    }

    override suspend fun deleteNote(note: Note) {
        val entity = mapper.toModel(entity = note)
        localDataSource.delete(entity = entity)
    }
}