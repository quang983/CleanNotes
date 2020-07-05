package com.example.domain.model

data class Note(
    var id: Long? = null,
    var groupId: Long? = null,
    val title: String,
    val description: String
)