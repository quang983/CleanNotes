package com.example.data.source

import com.example.data.entity.NoteModel

interface LocalNoteSource {

    suspend fun obtainAll(): List<NoteModel>
    suspend fun obtainById(id: Int): NoteModel
    suspend fun create(entity: NoteModel)
    suspend fun update(entity: NoteModel)
    suspend fun delete(entity: NoteModel)

}