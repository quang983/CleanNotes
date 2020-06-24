package com.example.data.source

import com.example.data.entity.NoteModel

interface LocalNoteSource {

    suspend fun obtainAll(): List<NoteModel>
    suspend fun obtainById(id: Long): NoteModel
    suspend fun create(entity: NoteModel)
    suspend fun update(entity: NoteModel)
    suspend fun delete(entity: NoteModel)
    suspend fun clear()

}