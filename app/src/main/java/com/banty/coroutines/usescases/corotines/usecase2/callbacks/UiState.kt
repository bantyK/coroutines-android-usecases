package com.banty.coroutines.usescases.corotines.usecase2.callbacks

import com.banty.coroutines.mock.AndroidVersion
import com.banty.coroutines.mock.VersionFeatures

sealed class UiState {
    object Loading : UiState()
    data class Success(val versionFeatures: VersionFeatures) : UiState()
    data class Error(val message: String) : UiState()
}
