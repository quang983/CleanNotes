package com.example.local.mapper

import com.example.data.entity.NoteModel
import com.example.local.entity.LocalNote

class LocalNoteMapper: LocalMapper<NoteModel, LocalNote> {

    override fun toModel(local: LocalNote): NoteModel {
        return NoteModel(
            title = local.title,
            description = local.description
        )
    }

    override fun toLocal(entity: NoteModel): LocalNote {
        return LocalNote(
            title = entity.title,
            description = entity.description
        )
    }
}