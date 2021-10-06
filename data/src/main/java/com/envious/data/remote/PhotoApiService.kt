package com.envious.data.remote

import com.envious.data.remote.response.CollectionItem
import com.envious.data.remote.response.SearchResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoApiService {

    @GET("/search/photos")
    suspend fun searchPhoto(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") limit: Int,
        @Query("order_by") orderBy: String,
        @Query("color") color: String
    ): Response<SearchResponse>

    @GET("/collections/{id}/photos")
    suspend fun getCollections(
        @Path("id") collectionId: Long,
        @Query("page") page: Int
    ): Response<List<CollectionItem>>

    companion object {
        operator fun invoke(retrofit: Retrofit): PhotoApiService = retrofit.create(PhotoApiService::class.java)
    }
}
