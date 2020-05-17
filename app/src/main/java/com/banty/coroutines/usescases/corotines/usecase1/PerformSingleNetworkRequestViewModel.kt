package com.banty.coroutines.usescases.corotines.usecase1

import androidx.lifecycle.viewModelScope
import com.banty.coroutines.base.BaseViewModel
import com.banty.coroutines.mock.MockApi
import kotlinx.coroutines.launch
import java.lang.Exception

class PerformSingleNetworkRequestViewModel (
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {


    fun performSingleNetworkRequest() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentAndroidVersion = mockApi.getRecentAndroidVersions()
                uiState.value = UiState.Success(recentAndroidVersion)
            } catch(exception: Exception) {
                exception.printStackTrace()
                uiState.value = UiState.Error("Network Request failed!!")
            }
        }
    }
}