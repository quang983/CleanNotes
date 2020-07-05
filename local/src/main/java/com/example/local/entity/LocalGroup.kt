package com.example.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalGroup(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val name: String
)