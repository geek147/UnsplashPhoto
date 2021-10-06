package com.envious.searchphoto.ui.main

import androidx.lifecycle.viewModelScope
import com.envious.data.dispatchers.CoroutineDispatchers
import com.envious.data.util.Constants
import com.envious.domain.model.Photo
import com.envious.domain.usecase.GetCollectionsUseCase
import com.envious.domain.util.Result
import com.envious.searchphoto.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val ioDispatchers: CoroutineDispatchers
) : BaseViewModel<MainViewModel.Intent, MainViewModel.State, MainViewModel.Effect>(MainViewModel.State()) {

    sealed class Intent {
        object GetCollection : Intent()
        data class LoadNext(val page: Int) : Intent()
    }

    data class State(
        val showLoading: Boolean = false,
        val showError: Boolean = false,
        val listPhoto: List<Photo> = listOf(),
        val viewState: ViewState = ViewState.Idle
    )

    sealed class Effect {
        data class ShowToast(val message: String) : Effect()
    }

    sealed class ViewState {
        object Idle : ViewState()
        object SuccessFirstInit : ViewState()
        object EmptyList : ViewState()
        object SuccessLoadMore : ViewState()
        object ErrorFirstInit : ViewState()
        object ErrorLoadMore : ViewState()
    }

    override fun onIntentReceived(intent: Intent) {
        when (intent) {
            is Intent.LoadNext -> {
                loadMoreUser(intent.page)
            }
            is Intent.GetCollection -> {
                getCollection()
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
