package com.example.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalNote(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val title: String,
    val description: String
) {
}