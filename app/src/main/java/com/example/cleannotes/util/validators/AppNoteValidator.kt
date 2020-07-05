package com.example.cleannotes.util.validators

import com.example.cleannotes.util.interfaces.NoteValidator
import javax.inject.Inject

class AppNoteValidator @Inject constructor(): NoteValidator {

    override fun validate(title: String, description: String): Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }

}