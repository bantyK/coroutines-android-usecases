package com.banty.coroutines.usescases.corotines.usecase3

import androidx.lifecycle.viewModelScope
import com.banty.coroutines.base.BaseViewModel
import com.banty.coroutines.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class PerformNetworkRequestsConcurrentViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsConcurrently() {
        startLoadingState()

        viewModelScope.launch {
            try {
                val oreoFeaturesDeferred = async { mockApi.getAndroidVersionFeatures(27) }
                val pieFeaturesDeferred = async { mockApi.getAndroidVersionFeatures(28) }
                val android10FeaturesDeferred = async { mockApi.getAndroidVersionFeatures(29) }


                val oreoFeatures = oreoFeaturesDeferred.await()
                val pieFeatures = pieFeaturesDeferred.await()
                val android10Features = android10FeaturesDeferred.await()

                val versionFeatues = listOf(oreoFeatures, pieFeatures, android10Features)

                uiState.value = UiState.Success(versionFeatues)
            } catch (exception: Exception) {
                uiState.value = UiState.Error(exception.message ?: "Network error")
            }
        }
    }

    fun performNetworkRequestsSequentially() {
        startLoadingState()
        viewModelScope.launch {
            try {
                val oreoFeatures = mockApi.getAndroidVersionFeatures(27)
                val pieFeatures = mockApi.getAndroidVersionFeatures(28)
                val android10Features = mockApi.getAndroidVersionFeatures(29)

                val versionFeatues = listOf(oreoFeatures, pieFeatures, android10Features)

                uiState.value = UiState.Success(versionFeatues)

            } catch (exception: Exception) {
                uiState.value = UiState.Error(exception.message ?: "Network error")
            }
        }

    }

    private fun startLoadingState() {
        uiState.value = UiState.Loading
    }

}