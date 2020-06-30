package com.example.cleannotes.util

import javax.inject.Inject

class NoteValidator @Inject constructor(): Validator {

    override fun validate(title: String, description: String): Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }

}