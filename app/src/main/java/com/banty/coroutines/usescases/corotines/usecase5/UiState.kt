package com.banty.coroutines.usescases.corotines.usecase5

import com.banty.coroutines.mock.VersionFeatures

sealed class UiState {
    object Loading : UiState()

    data class Success(
        val versionFeatures: List<VersionFeatures>
    ) : UiState()

    data class Error(val message: String) : UiState()
}