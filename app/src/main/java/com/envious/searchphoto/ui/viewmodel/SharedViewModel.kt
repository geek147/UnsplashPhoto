package com.envious.searchphoto.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.envious.data.dispatchers.CoroutineDispatchers
import com.envious.data.util.Constants
import com.envious.data.util.Constants.SHARED_KEY_COLOR
import com.envious.data.util.Constants.SHARED_KEY_ORIENTATION
import com.envious.data.util.Constants.SHARED_KEY_SORT_BY
import com.envious.data.util.Filter
import com.envious.data.util.Orientation
import com.envious.data.util.Sort
import com.envious.domain.usecase.GetCollectionsUseCase
import com.envious.domain.usecase.SearchPhotoUseCase
import com.envious.domain.util.Result
import com.envious.searchphoto.base.BaseViewModel
import com.envious.searchphoto.util.Intent
import com.envious.searchphoto.util.State
import com.envious.searchphoto.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val searchPhotoUseCase: SearchPhotoUseCase,
    private val ioDispatchers: CoroutineDispatchers,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel<Intent, State>(State()) {

    override fun onIntentReceived(intent: Intent) {
        when (intent) {
            is Intent.LoadNextCollection -> {
                loadMoreCollection(intent.page)
            }
            is Intent.GetCollection -> {
                getCollection()
            }
            is Intent.LoadNextSearch -> {
                loadMoreSearch(intent.page)
            }
            is Intent.SearchPhoto -> {
                searchPhotos(intent.query)
            }
            Intent.GetDefaultSetting -> {
                getDefaultSettings()
            }
            is Intent.SetSettings -> {
                setSettings(
                    sort = intent.sort,
                    orientation = intent.orientation,
                    filter = intent.filter
                )
            }
        }
    }

    private fun getCollection() {
        setState {
            copy(
                showLoading = true,
                showError = false
            )
        }

        viewModelScope.launch {
            when (
                val result = withContext(ioDispatchers.io) {
                    getCollectionsUseCase(
                        id = Constants.COLLECTION_DEFAULT_ID,
                        limit = 10,
                        page = 1,
                        orientation = Constants.COLLECTION_DEFAULT_ORIENTATION
                    )
                }
            ) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setState {
                            copy(
                                listPhoto = emptyList(),
                                showLoading = false,
                                showError = false,
                                viewState = ViewState.EmptyList
                            )
                        }
                    } else {
                        setState {
                            copy(
                                listPhoto = result.data,
                                showLoading = false,
                                showError = false,
                                viewState = ViewState.SuccessFirstInit
                            )
                        }
                    }
                }
                is Result.Error -> {
                    setState {
                        copy(
                            listPhoto = emptyList(),
                            showLoading = false,
                            showError = true,
                            viewState = ViewState.ErrorFirstInit
                        )
                    }
                }
            }
        }
    }

    private fun loadMoreCollection(page: Int) {
        setState {
            copy(
                showLoading = true,
                showError = false
            )
        }

        viewModelScope.launch {
            when (
                val result = withContext(ioDispatchers.io) {
                    getCollectionsUseCase(
                        id = Constants.COLLECTION_DEFAULT_ID,
                        limit = 10,
                        page = page,
                        orientation = Constants.COLLECTION_DEFAULT_ORIENTATION
                    )
                }
            ) {
                is Result.Success -> {
                    setState {
                        copy(
                            listPhoto = result.data,
                            showLoading = false,
                            showError = false,
                            viewState = ViewState.SuccessLoadMore
                        )
                    }
                }
                is Result.Error -> {
                    setState {
                        copy(
                            listPhoto = emptyList(),
                            showLoading = false,
                            showError = true,
                            viewState = ViewState.ErrorLoadMore
                        )
                    }
                }
            }
        }
    }

    private fun searchPhotos(query: String) {
        setState {
            copy(
                showLoading = true,
                showError = false,
                query = query
            )
        }

        viewModelScope.launch {
            when (
                val result = withContext(ioDispatchers.io) {
                    searchPhotoUseCase(
                        query = query,
                        limit = 10,
                        page = 1,
                        orderBy = Sort.relevant.name,
                        color = Filter.black_and_white.name
                    )
                }
            ) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setState {
                            copy(
                                listPhoto = emptyList(),
                                showLoading = false,
                                showError = false,
                                viewState = ViewState.EmptyList
                            )
                        }
                    } else {
                        setState {
                            copy(
                                listPhoto = result.data,
                                showLoading = false,
                                showError = false,
                                viewState = ViewState.SuccessFirstInit
                            )
                        }
                    }
                }
                is Result.Error -> {
                    setState {
                        copy(
                            listPhoto = emptyList(),
                            showLoading = false,
                            showError = true,
                            viewState = ViewState.ErrorFirstInit
                        )
                    }
                }
            }
        }
    }

    private fun loadMoreSearch(page: Int) {
        setState {
            copy(
                showLoading = true,
                showError = false
            )
        }

        viewModelScope.launch {
            when (
                val result = withContext(ioDispatchers.io) {
                    searchPhotoUseCase(
                        query = state.value?.query.orEmpty(),
                        limit = 10,
                        page = 1,
                        orderBy = Sort.relevant.name,
                        color = Filter.black_and_white.name
                    )
                }
            ) {
                is Result.Success -> {
                    setState {
                        copy(
                            listPhoto = result.data,
                            showLoading = false,
                            showError = false,
                            viewState = ViewState.SuccessLoadMore
                        )
                    }
                }
                is Result.Error -> {
                    setState {
                        copy(
                            listPhoto = emptyList(),
                            showLoading = false,
                            showError = true,
                            viewState = ViewState.ErrorLoadMore
                        )
                    }
                }
            }
        }
    }

    private fun getDefaultSettings() {
        val keySort = sharedPreferences.getString(SHARED_KEY_SORT_BY, Sort.relevant.name)
        val keyColor = sharedPreferences.getString(SHARED_KEY_COLOR, Filter.any.name)
        val keyOrientation = sharedPreferences.getString(SHARED_KEY_ORIENTATION, Orientation.any.name)

        setState {
            copy(
                sort = Sort.valueOf(keySort.orEmpty()),
                filter = Filter.valueOf(keyColor.orEmpty()),
                orientation = Orientation.valueOf(keyOrientation.orEmpty())
            )
        }
    }

    private fun setSettings(sort: String, filter: String, orientation: String) {
        sharedPreferences.edit().putString(SHARED_KEY_SORT_BY, sort).apply()
        sharedPreferences.edit().putString(SHARED_KEY_COLOR, filter).apply()
        sharedPreferences.edit().putString(SHARED_KEY_ORIENTATION, orientation).apply()

        setState {
            copy(
                sort = Sort.valueOf(sort),
                filter = Filter.valueOf(filter),
                orientation = Orientation.valueOf(orientation),
                viewState = ViewState.BackToSearchResult
            )
        }
    }
}
