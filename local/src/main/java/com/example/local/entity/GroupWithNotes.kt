package com.example.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GroupWithNotes(
    @Embedded val group: LocalGroup,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val notes: List<LocalNote>
)