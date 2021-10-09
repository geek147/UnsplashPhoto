package com.envious.data.repository

import android.util.Log
import com.envious.data.mapper.CollectionItemRemoteMapper
import com.envious.data.mapper.PhotoItemRemoteMapper
import com.envious.data.remote.PhotoApiService
import com.envious.domain.model.Photo
import com.envious.domain.repository.PhotoRepository
import com.envious.domain.util.Result
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService
) : PhotoRepository {

    override suspend fun searchPhotos(
        query: String,
        page: Int,
        limit: Int,
        orderBy: String,
        color: String,
    ): Result<List<Photo>> {
        return try {
            val result = photoApiService.searchPhoto(
                query = query,
                page = page,
                limit = limit,
//            orderBy = orderBy,
//            color = color
            )
            if (result.isSuccessful) {
                val remoteMapper = PhotoItemRemoteMapper()
                val remoteData = result.body()
                val items = remoteData?.photoItems
                return if (remoteData != null && !items.isNullOrEmpty()) {
                    val listData = mutableListOf<Photo>()
                    items.forEach {
                        listData.add(remoteMapper.transform(it!!))
                    }
                    Result.Success(listData)
                } else {
                    Result.Success(emptyList())
                }
            } else {
                return Result.Error(Exception("Invalid data/failure"))
            }
        } catch (e: Exception) {
            Log.e("ApiCalls", "Call error: ${e.localizedMessage}", e.cause)
            Result.Error(Exception("Invalid data/failure"))
        }
    }

    override suspend fun getCollections(
        id: Long,
        page: Int,
        limit: Int,
        orientation: String,
    ): Result<List<Photo>> {
        return try {
            val result = photoApiService.getCollections(
                collectionId = id,
                page = page,
                limit = limit
            )
            return if (result.isSuccessful) {
                val remoteMapper = CollectionItemRemoteMapper()
                val remoteData = result.body()
                if (remoteData != null && !remoteData.isNullOrEmpty()) {
                    val listData = mutableListOf<Photo>()
                    remoteData.forEach {
                        listData.add(remoteMapper.transform(it))
                    }
                    Result.Success(listData)
                } else {
                    Result.Success(emptyList())
                }
            } else {
                Result.Error(Exception("Invalid data/failure"))
            }
        } catch (e: Exception) {
            Log.e("ApiCalls", "Call error: ${e.localizedMessage}", e.cause)
            Result.Error(Exception("Invalid data/failure"))
        }
    }
}
