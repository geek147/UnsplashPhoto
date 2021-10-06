package com.envious.domain.repository

import com.envious.domain.model.Photo
import com.envious.domain.util.Result

interface PhotoRepository {
    suspend fun searchPhotos(
        query: String,
        page: Int,
        limit: Int,
        orderBy: String,
        color: String
    ): Result<List<Photo>>

    suspend fun getCollections(
        id: Long,
        page: Int,
        limit: Int,
        orientation: String
    ): Result<List<Photo>>
}
