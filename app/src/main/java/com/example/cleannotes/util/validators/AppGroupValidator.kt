package com.example.cleannotes.util.validators

import com.example.cleannotes.util.interfaces.GroupValidator
import javax.inject.Inject

class AppGroupValidator @Inject constructor(): GroupValidator {

    override fun validate(name: String): Boolean {
        return name.isNotEmpty()
    }
}