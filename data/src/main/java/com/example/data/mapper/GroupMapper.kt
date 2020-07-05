package com.example.data.mapper

import com.example.data.entity.GroupModel
import com.example.domain.model.Group
import javax.inject.Inject

class GroupMapper @Inject constructor(): BaseMapper<GroupModel, Group> {

    override fun toModel(entity: Group): GroupModel {
        return GroupModel(
            id = entity.id,
            name = entity.name
        )
    }

    override fun toEntity(model: GroupModel): Group {
        return Group(
            id = model.id,
            name = model.name
        )
    }
}