package com.envious.searchphoto.util

import com.envious.domain.model.Photo

sealed class Intent {
    object GetCollection : Intent()
    data class SearchPhoto(val query: String) : Intent()
    data class LoadNextSearch(val page: Int) : Intent()
    data class LoadNextCollection(val page: Int) : Intent()
}

data class State(
    val showLoading: Boolean = false,
    val showError: Boolean = false,
    val listPhoto: List<Photo> = listOf(),
    val viewState: ViewState = ViewState.Idle,
    val query: String = ""
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
