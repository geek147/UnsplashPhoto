package com.envious.data

import com.envious.data.remote.SearchPhotoUseCaseImpl
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
class SearchPhotoUseCaseTest {

    private val repository: PhotoRepository = mockk()
    private var searchPhotoUseCase: SearchPhotoUseCaseImpl = mockk()

    @Before
    fun setUp() {
        searchPhotoUseCase = SearchPhotoUseCaseImpl(repository)
    }

    @Test
    fun verify_searchPhotoUseCase_call_getPhotoRepository() {
        val emptyList = emptyList<Photo>()
        val query: String = "ABC"
        val page: Int = 1
        val limit: Int = 10
        val order: String = "latest"
        val color = "black"

        coEvery {
            repository.searchPhotos(any(), any(), any(), any(), any())
        } returns Result.Success(emptyList)

        runBlockingTest {
            searchPhotoUseCase(
                page = page,
                limit = limit,
                query = query,
                orderBy = order,
                color = color
            )
        }

        coVerify {
            repository.searchPhotos(
                page = page,
                limit = limit,
                query = query,
                orderBy = order,
                color = color
            )
        }
    }
}
