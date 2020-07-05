package com.example.local.database.dao

import androidx.room.*
import com.example.local.entity.GroupWithNotes
import com.example.local.entity.LocalGroup

@Dao
interface LocalGroupDao {

    @Query("SELECT * FROM LocalGroup")
    suspend fun obtainAllLocalGroups(): List<LocalGroup>

    @Transaction
    @Query("SELECT * FROM LocalGroup where id LIKE :groupId")
    suspend fun obtainLocalGroup(groupId: Long): GroupWithNotes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalGroup(localGroup: LocalGroup)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLocalGroup(localGroup: LocalGroup)

    @Query("DELETE FROM LocalGroup WHERE id LIKE :groupId")
    suspend fun deleteLocalGroup(groupId: Long)
}
