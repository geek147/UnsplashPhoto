package com.envious.domain.util

sealed class Result<out T : Any> {

    data class Success<T : Any>(val data: List<T>) : Result<List<T>>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Loading -> "Loading"
        }
    }
}
