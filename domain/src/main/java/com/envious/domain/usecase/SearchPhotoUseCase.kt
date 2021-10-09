package com.envious.domain.usecase

import com.envious.domain.model.Photo
import com.envious.domain.util.Result

interface SearchPhotoUseCase {
    suspend operator fun invoke(
        query: String,
        page: Int,
        limit: Int,
        orderBy: String,
        color: String,
        orientation: String
    ): Result<List<Photo>>
}
