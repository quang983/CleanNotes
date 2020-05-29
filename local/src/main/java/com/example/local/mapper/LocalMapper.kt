package com.example.local.mapper

interface LocalMapper<M, L> {

    fun toModel(local: L): M
    fun toLocal(entity: M): L

}