package com.envious.data

import com.envious.data.remote.PhotoApiService
import com.envious.data.remote.response.CollectionItem
import com.envious.data.remote.response.SearchResponse
import com.envious.data.repository.PhotoRepositoryImpl
import com.envious.data.util.Constants
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class PhotoRepositoryTest {

    private val apiService: PhotoApiService = mockk()

    private lateinit var repositoryTest: PhotoRepositoryImpl

    private val id: Long = Constants.COLLECTION_DEFAULT_ID
    private val page: Int = 1
    private val limit: Int = 10
    private val orientation: String = Constants.COLLECTION_DEFAULT_ORIENTATION
    private val query: String = "test"
    private val orderBy: String = "latest"
    private val filter: String = "black"

    @Before
    fun setUp() {
        repositoryTest = PhotoRepositoryImpl(apiService)
    }

    @Test
    fun getCollection_returnSuccessPhotos() {

        coEvery {
            apiService.getCollections(any(), any(), any())
        } returns Response.success(
            listOf(
                CollectionItem()
            )
        )

        runBlockingTest {
            repositoryTest.getCollections(id, page, limit, orientation)
        }

        coVerify {
            apiService.getCollections(id, page, limit)
        }
    }

    @Test
    fun searchPhoto_returnSuccessPhotos() {

        coEvery {
            apiService.searchPhoto(any(), any(), any())
        } returns Response.success(
            SearchResponse()
        )

        runBlockingTest {
            repositoryTest.searchPhotos(query, page, limit, orderBy, filter)
        }

        coVerify {
            apiService.searchPhoto(query, page, limit)
        }
    }
}
