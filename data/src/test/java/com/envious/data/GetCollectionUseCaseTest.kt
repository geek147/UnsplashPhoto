package com.envious.data

import com.envious.data.remote.GetCollectionUseCaseImpl
import com.envious.data.util.Constants
import com.envious.domain.model.Photo
import com.envious.domain.repository.PhotoRepository
import com.envious.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCollectionUseCaseTest {

    private val repository: PhotoRepository = mockk()
    private var getCollection: GetCollectionUseCaseImpl = mockk()

    @Before
    fun setUp() {
        getCollection = GetCollectionUseCaseImpl(repository)
    }

    @Test
    fun getCollection_call_getPhotoRepository() {
        val emptyList = emptyList<Photo>()
        val id: Long = Constants.COLLECTION_DEFAULT_ID
        val page: Int = 1
        val limit: Int = 10
        val orientation: String = Constants.COLLECTION_DEFAULT_ORIENTATION

        coEvery {
            repository.getCollections(any(), any(), any(), any())
        } returns Result.Success(emptyList)

        runBlockingTest {
            getCollection(
                id = id,
                page = page,
                limit = limit,
                orientation = orientation
            )
        }

        coVerify {
            repository.getCollections(
                id = id,
                page = page,
                limit = limit,
                orientation = orientation
            )
        }
    }
}
