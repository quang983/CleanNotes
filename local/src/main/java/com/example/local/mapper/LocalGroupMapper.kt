package com.example.local.mapper

import com.example.data.entity.GroupModel
import com.example.local.entity.LocalGroup
import javax.inject.Inject

class LocalGroupMapper @Inject constructor(): LocalMapper<GroupModel, LocalGroup> {

    override fun toModel(local: LocalGroup): GroupModel {
        return GroupModel(
            id = local.id,
            name = local.name
        )
    }

    override fun toLocal(entity: GroupModel): LocalGroup {
        return LocalGroup(
            id = entity.id ?: 0,
            name = entity.name
        )
    }
}