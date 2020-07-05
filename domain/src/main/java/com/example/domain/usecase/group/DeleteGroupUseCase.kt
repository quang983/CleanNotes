package com.example.domain.usecase.group

import com.example.domain.repository.GroupRepository
import javax.inject.Inject

class DeleteGroupUseCase @Inject constructor(
    private val repository: GroupRepository
) {

    suspend operator fun invoke(groupId: Long) {
        repository.deleteGroup(groupId = groupId)
    }

}