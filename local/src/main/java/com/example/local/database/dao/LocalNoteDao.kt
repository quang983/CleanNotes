package com.example.local.database.dao

import androidx.room.*
import com.example.local.entity.LocalNote

@Dao
interface LocalNoteDao {

    @Query("SELECT * FROM LocalNote")
    suspend fun getAllLocalNotes(): List<LocalNote>

    @Query("SELECT * FROM LocalNote WHERE id LIKE :id")
    suspend fun getLocalNoteById(id: Long): LocalNote

    @Insert
    suspend fun addNewLocalNote(note: LocalNote)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLocalNote(note: LocalNote)

    @Delete
    suspend fun deleteLocalNote(note: LocalNote)

    @Query("DELETE FROM LocalNote")
    suspend fun clearAllNotes()
}