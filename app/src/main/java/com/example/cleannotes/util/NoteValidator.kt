package com.example.cleannotes.util

class NoteValidator: Validator {

    override fun validate(title: String, description: String): Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }

}