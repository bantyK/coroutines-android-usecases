package com.banty.coroutines.usescases.corotines.usecase5

import androidx.lifecycle.viewModelScope
import com.banty.coroutines.base.BaseViewModel
import com.banty.coroutines.mock.MockApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber

class ExceptionHandlingViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun showResultsEvenIfChildCoroutineFails() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            supervisorScope {
                val oreoFeaturesDeferred = async { api.getAndroidVersionFeatures(27) }
                val pieFeaturesDeferred = async { api.getAndroidVersionFeatures(28) }
                val android10FeaturesDeferred = async { api.getAndroidVersionFeatures(29) }

                val oreoFeatures = try {
                    oreoFeaturesDeferred.await()
                } catch (e: Exception) {
                    Timber.e("Error loading Oreo featured")
                    null
                }

                val pieFeatures = try {
                    pieFeaturesDeferred.await()
                } catch (e: Exception) {
                    Timber.e("Error loading Pie featured")
                    null
                }

                val android10Features = try {
                    android10FeaturesDeferred.await()
                } catch (e: Exception) {
                    Timber.e("Error loading android10 featured")
                    null
                }

                val versionFeatures = listOfNotNull(oreoFeatures, pieFeatures, android10Features)
                uiState.value = UiState.Success(versionFeatures)
            }
        }
    }


    fun handleExceptionHandlingWithTryCatch() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                api.getAndroidVersionFeatures(27)
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Request failed: ${exception.message}")
            }
        }
    }

    fun handleWithCoroutineExceptionHandler() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            uiState.value = UiState.Error("Network request failed: ${exception.message}")
        }
        uiState.value = UiState.Loading

        viewModelScope.launch(exceptionHandler) {
            api.getAndroidVersionFeatures(27)
        }
    }

}