package com.example.data.mapper

import com.example.data.entity.NoteModel
import com.example.domain.model.Note
import javax.inject.Inject


class NoteMapper @Inject constructor(): BaseMapper<NoteModel, Note> {

    override fun toModel(entity: Note): NoteModel {
        return NoteModel(
            id = entity.id,
            title = entity.title,
            description = entity.description
        )
    }

    override fun toEntity(model: NoteModel): Note {
        return Note(
            id = model.id,
            title = model.title,
            description = model.description
        )
    }


}

