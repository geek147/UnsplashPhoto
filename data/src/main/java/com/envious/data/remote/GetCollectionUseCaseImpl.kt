package com.envious.data.remote

import com.envious.domain.model.Photo
import com.envious.domain.repository.PhotoRepository
import com.envious.domain.usecase.GetCollectionsUseCase
import com.envious.domain.util.Result
import javax.inject.Inject

class GetCollectionUseCaseImpl @Inject constructor(
    private val repository: PhotoRepository
) : GetCollectionsUseCase {

    override suspend fun invoke(
        id: Long,
        page: Int,
        limit: Int,
        orientation: String,
    ): Result<List<Photo>> {
        return repository.getCollections(id, page, limit, orientation)
    }
}
