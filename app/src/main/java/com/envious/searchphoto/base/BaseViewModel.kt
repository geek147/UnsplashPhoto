package com.envious.searchphoto.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<Intent, State>(initialState: State) : ViewModel() {

    protected var viewState = initialState

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    abstract fun onIntentReceived(intent: Intent)

    protected fun setState(state: State.() -> State) {
        viewState = state.invoke(viewState)
        _state.value = state.invoke(viewState)
    }

    @VisibleForTesting
    fun mockState(state: State.() -> State) {
        setState(state)
    }
}
