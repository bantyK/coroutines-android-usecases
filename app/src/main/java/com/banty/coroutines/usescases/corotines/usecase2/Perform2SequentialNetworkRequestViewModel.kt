package com.banty.coroutines.usescases.corotines.usecase2

import androidx.lifecycle.viewModelScope
import com.banty.coroutines.base.BaseViewModel
import com.banty.coroutines.mock.MockApi
import kotlinx.coroutines.launch

class Perform2SequentialNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val recentVersions = mockApi.getRecentAndroidVersions()
                val mostRecentVersion = recentVersions.last()

                val featuresOfMostRecentVersion =
                    mockApi.getAndroidVersionFeatures(mostRecentVersion.apiLevel)

                uiState.value = UiState.Success(featuresOfMostRecentVersion)
            } catch (exception: Exception) {
                exception.printStackTrace()
                uiState.value = UiState.Error(exception.message ?: "Network request failed")
            }
        }
    }
}