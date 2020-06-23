package com.example.cleannotes.util

interface Validator {
    fun validate(title: String, description: String): Boolean
}