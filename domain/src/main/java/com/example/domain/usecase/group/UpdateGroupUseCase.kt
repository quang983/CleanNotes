package com.example.domain.usecase.group

import com.example.domain.model.Group
import com.example.domain.repository.GroupRepository
import javax.inject.Inject

class UpdateGroupUseCase @Inject constructor(
    private val repository: GroupRepository
) {

    suspend operator fun invoke(group: Group) {
        repository.updateGroup(group = group)
    }

}