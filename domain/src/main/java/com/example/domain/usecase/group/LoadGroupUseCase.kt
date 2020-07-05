package com.example.domain.usecase.group

import com.example.domain.model.Note
import com.example.domain.repository.GroupRepository
import javax.inject.Inject

class LoadGroupUseCase @Inject constructor(
    private val repository: GroupRepository
) {

    suspend operator fun invoke(groupId: Long): List<Note> {
        return repository.loadGroup(groupId = groupId)
    }

}