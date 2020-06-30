package com.example.local.mapper

import com.example.data.entity.NoteModel
import com.example.local.entity.LocalNote
import javax.inject.Inject

class LocalNoteMapper @Inject constructor(): LocalMapper<NoteModel, LocalNote> {

    override fun toModel(local: LocalNote): NoteModel {
        return NoteModel(
            id = local.id,
            title = local.title,
            description = local.description
        )
    }

    override fun toLocal(entity: NoteModel): LocalNote {
        return LocalNote(
            id = entity.id ?: 0,
            title = entity.title,
            description = entity.description
        )
    }
}