package com.banty.coroutines.usescases.corotines.usecase2

import com.banty.coroutines.mock.VersionFeatures

sealed class UiState {
    object Loading : UiState()
    data class Success(val versionFeatures: VersionFeatures) : UiState()
    data class Error(val errorMessage: String) : UiState()
}