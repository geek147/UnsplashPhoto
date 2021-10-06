package com.envious.data.mapper

import com.envious.data.remote.response.SearchResponse
import com.envious.domain.model.Photo

class PhotoItemRemoteMapper : BaseMapperRepository<SearchResponse.PhotoItem, Photo> {
    override fun transform(item: SearchResponse.PhotoItem): Photo = Photo(
        description = item.description.orEmpty(),
        id = item.id.orEmpty(),
        blurHash = item.blurHash.orEmpty(),
        urls = Photo.Urls(
            full = item.urls?.full.orEmpty(),
            raw = item.urls?.raw.orEmpty(),
            regular = item.urls?.regular.orEmpty(),
            small = item.urls?.small.orEmpty(),
            thumb = item.urls?.thumb.orEmpty()
        ),
        username = item.user?.name.orEmpty() ,
    )

    override fun transformToRepository(item: Photo): SearchResponse.PhotoItem =
        SearchResponse.PhotoItem()
}