package com.envious.domain.usecase

import com.envious.domain.model.Photo
import com.envious.domain.util.Result

interface GetCollectionsUseCase {
    suspend operator fun invoke(
        id: Long,
        page: Int,
        limit: Int,
        orientation: String
    ): Result<List<Photo>>
}
