package com.example.domain.usecase.group

import com.example.domain.model.Group
import com.example.domain.repository.GroupRepository
import javax.inject.Inject

class LoadGroupsUseCase @Inject constructor(
    private val repository: GroupRepository
) {

    suspend operator fun invoke(): List<Group> {
        return repository.loadGroups()
    }

}