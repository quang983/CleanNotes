package com.example.cleannotes.util.interfaces

interface NoteValidator {
    fun validate(title: String, description: String): Boolean
}
