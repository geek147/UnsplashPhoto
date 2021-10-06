package com.envious.data.mapper

interface BaseMapperRepository<E, D> {

    fun transform(item: E): D

    fun transformToRepository(item: D): E
}
