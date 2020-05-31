package com.example.domain.model

data class Note(
    var id: Long? = null,
    val title: String,
    val description: String
)