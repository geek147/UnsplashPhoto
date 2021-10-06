package com.envious.data.remote

import com.envious.domain.model.Photo
import com.envious.domain.repository.PhotoRepository
import com.envious.domain.usecase.SearchPhotoUseCase
import com.envious.domain.util.Result

class SearchPhotoUseCaseImpl(private val repository: PhotoRepository) : SearchPhotoUseCase {

    override suspend fun invoke(
        query: String,
        page: Int,
        limit: Int,
        orderBy: String,
        color: String,
    ): Result<List<Photo>> {
        return repository.searchPhotos(
            query = query,
            page = page,
            limit = limit,
            orderBy = orderBy,
            color = color
        )
    }
}
