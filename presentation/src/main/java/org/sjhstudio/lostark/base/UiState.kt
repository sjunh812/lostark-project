package org.sjhstudio.lostark.base

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Fail(val message: String) : UiState<Nothing>()
    data class Error(val error: Throwable) : UiState<Nothing>()
}

fun <T> UiState<T>.successOrNull(): T? = if (this is UiState.Success<T>) data else null