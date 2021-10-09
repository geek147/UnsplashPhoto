package com.envious.searchphoto

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.envious.data.dispatchers.CoroutineDispatchers
import com.envious.data.util.Constants
import com.envious.domain.model.Photo
import com.envious.domain.usecase.GetCollectionsUseCase
import com.envious.domain.usecase.SearchPhotoUseCase
import com.envious.domain.util.Result
import com.envious.searchphoto.ui.viewmodel.SharedViewModel
import com.envious.searchphoto.util.Intent
import com.envious.searchphoto.util.State
import com.envious.searchphoto.util.ViewState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SharedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var getCollection = mockk<GetCollectionsUseCase>()
    private var searchPhotos = mockk<SearchPhotoUseCase>()
    private var sharedPreferences = mockk<SharedPreferences>()
    private var ioDispatcher = mockk<CoroutineDispatchers>()

    private val observedStateList = mutableListOf<State>()
    private val observerState = mockk<Observer<State>>()
    private val slotState = slot<State>()

    private val testDispatcher = TestCoroutineDispatcher()

    private val viewModel = SharedViewModel(
        getCollection,
        searchPhotos,
        ioDispatcher,
        sharedPreferences
    )

    private val query = "test"

    private val photo = Photo(
        description = "star wars",
        id = "1234",
        blurHash = "",
        urls = Photo.Urls(
            full = "www.google.com/1",
            regular = "www.google.com/1",
            thumb = "www.google.com/1",
            raw = "www.google.com/1",
            small = "www.google.com/1"
        ),
        username = "test123"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        viewModel.state.observeForever(observerState)

        every {
            observerState.onChanged(capture(slotState))
        } answers {
            observedStateList.add(slotState.captured)
        }
    }

    @After
    fun tearDown() {
        observedStateList.clear()

        viewModel.state.removeObserver(observerState)
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `onGetCollection loading showLoading`() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getCollection(any(), any(), any(), any())
        } returns Result.Success(listOf(photo))

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_SORT_BY, "relevant")
        } returns "relevant"

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_COLOR, "any")
        } returns "any"

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_ORIENTATION, "any")
        } returns "any"

        viewModel.onIntentReceived(
            Intent.GetCollection
        )

        assertEquals(observedStateList.first().showLoading, true)
    }

    @Test
    fun `onGetCollection success should set view state to Success  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getCollection(any(), any(), any(), any())
        } returns Result.Success(listOf(photo))

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_SORT_BY, "relevant")
        } returns "relevant"

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_COLOR, "any")
        } returns "any"

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_ORIENTATION, "any")
        } returns "any"

        viewModel.onIntentReceived(
            Intent.GetCollection
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.SuccessFirstInit)
        assertEquals(observedStateList.last().listPhoto[0].id, "1234")
    }

    @Test
    fun `onGetCollection success but empty should set view state to EmptyState  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getCollection(any(), any(), any(), any())
        } returns Result.Success(
            emptyList()
        )

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_SORT_BY, "relevant")
        } returns "relevant"

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_COLOR, "any")
        } returns "any"

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_ORIENTATION, "any")
        } returns "any"

        viewModel.onIntentReceived(
            Intent.GetCollection
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.EmptyListFirstInit)
    }

    @Test
    fun `onGetCollection error should set view state to Error State  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getCollection(any(), any(), any(), any())
        } returns Result.Error(Exception("Invalid data/failure"))

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_SORT_BY, "relevant")
        } returns "relevant"

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_COLOR, "any")
        } returns "any"

        coEvery {
            sharedPreferences.getString(Constants.SHARED_KEY_ORIENTATION, "any")
        } returns "any"

        viewModel.onIntentReceived(
            Intent.GetCollection
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.ErrorFirstInit)
    }

    @Test
    fun `onSearchPhoto loading showLoading`() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        viewModel.onIntentReceived(
            Intent.SearchPhoto(query)
        )

        assertEquals(observedStateList.first().showLoading, true)
    }

    @Test
    fun `onSearchPhoto success should set view state to Success  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            searchPhotos(any(), any(), any(), any(), any(), any())
        } returns Result.Success(listOf(photo))

        viewModel.onIntentReceived(
            Intent.SearchPhoto(query)
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.SuccessFirstInit)
        assertEquals(observedStateList.last().listPhoto[0].id, "1234")
    }

    @Test
    fun `onSearchPhoto success but empty should set view state to EmptyState  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            searchPhotos(any(), any(), any(), any(), any(), any())
        } returns Result.Success(
            emptyList()
        )

        viewModel.onIntentReceived(
            Intent.SearchPhoto(query)
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.EmptyListFirstInit)
    }

    @Test
    fun `onSearchPhotos error should set view state to Error State  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            searchPhotos(any(), any(), any(), any(), any(), any())
        } returns Result.Error(Exception("Invalid data/failure"))

        viewModel.onIntentReceived(
            Intent.SearchPhoto(query)
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.ErrorFirstInit)
    }
}
