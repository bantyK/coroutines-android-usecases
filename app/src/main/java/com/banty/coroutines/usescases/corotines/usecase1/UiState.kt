package com.banty.coroutines.usescases.corotines.usecase1

import com.banty.coroutines.mock.AndroidVersion

sealed class UiState {
    object Loading: UiState()
    data class Success(val recentVersions: List<AndroidVersion>): UiState()
    data class Error(val message: String): UiState()
}