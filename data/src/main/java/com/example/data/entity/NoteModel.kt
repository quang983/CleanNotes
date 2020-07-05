package com.example.data.entity

data class NoteModel(
    var id: Long? = null,
    var groupId: Long? = null,
    val title: String,
    val description: String
)