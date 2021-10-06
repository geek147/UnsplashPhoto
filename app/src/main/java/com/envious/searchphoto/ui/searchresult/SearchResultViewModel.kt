package com.envious.searchphoto.ui.searchresult

import androidx.lifecycle.viewModelScope
import com.envious.data.dispatchers.CoroutineDispatchers
import com.envious.data.util.Filter
import com.envious.data.util.Sort
import com.envious.domain.usecase.SearchPhotoUseCase
import com.envious.domain.util.Result
import com.envious.searchphoto.base.BaseViewModel
import com.envious.searchphoto.util.Effect
import com.envious.searchphoto.util.Intent
import com.envious.searchphoto.util.State
import com.envious.searchphoto.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchPhotoUseCase: SearchPhotoUseCase,
    private val ioDispatchers: CoroutineDispatchers
) : BaseViewModel<Intent, State, Effect>(State()) {

    override fun onIntentReceived(intent: Intent) {
        when (intent) {
            is Intent.LoadNext -> {
                loadMoreUser(intent.page)
            }
            is Intent.SearchPhoto -> {
                searchPhotos(intent.query)
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
                    setEffect(Effect.ShowToast("Gagal menambahkan data baru"))
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

    private fun loadMoreUser(page: Int) {
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
                    setEffect(Effect.ShowToast("Gagal menambahkan data baru"))
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
}
