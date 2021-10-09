package com.envious.searchphoto.util

import com.envious.data.util.Filter
import com.envious.data.util.Orientation
import com.envious.data.util.Sort
import com.envious.domain.model.Photo

sealed class Intent {
    object GetCollection : Intent()
    data class SearchPhoto(val query: String) : Intent()
    data class LoadNextSearch(val page: Int) : Intent()
    data class LoadNextCollection(val page: Int) : Intent()
    data class SetSettings(val sort: String, val filter: String, val orientation: String) : Intent()
}

data class State(
    val showLoading: Boolean = false,
    val listPhoto: List<Photo> = listOf(),
    val viewState: ViewState = ViewState.Idle,
    val query: String = "",
    val sort: Sort = Sort.relevant,
    val filter: Filter = Filter.black_and_white,
    val orientation: Orientation = Orientation.portrait
)

sealed class Effect {
    data class ShowToast(val message: String) : Effect()
    data class ReturnToSearchResult(val message: String) : Effect()
}

sealed class ViewState {
    object Idle : ViewState()
    object SuccessFirstInit : ViewState()
    object EmptyList : ViewState()
    object SuccessLoadMore : ViewState()
    object ErrorFirstInit : ViewState()
    object ErrorLoadMore : ViewState()
    object BackToSearchResult : ViewState()
}
