package com.banty.coroutines.usescases.corotines.usecase4

import androidx.lifecycle.viewModelScope
import com.banty.coroutines.base.BaseViewModel
import com.banty.coroutines.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class NetworkRequestTimeoutViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                withTimeout(timeout) {
                    val recentVersion = api.getRecentAndroidVersions()
                    uiState.value = UiState.Success(recentVersion)
                }
            } catch (timeoutCancellationException: TimeoutCancellationException) {
                uiState.value = UiState.Error("Network request failed due to timeout!!")
            } catch(exception: Exception) {
                uiState.value = UiState.Error("Network request failed!!")
            }
        }
    }

}