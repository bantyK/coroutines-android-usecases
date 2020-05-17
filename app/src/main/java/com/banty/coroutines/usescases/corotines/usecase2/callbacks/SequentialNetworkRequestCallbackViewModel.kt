package com.banty.coroutines.usescases.corotines.usecase2.callbacks

import androidx.lifecycle.ViewModel
import com.banty.coroutines.base.BaseViewModel
import com.banty.coroutines.mock.AndroidVersion
import com.banty.coroutines.mock.MockApi
import com.banty.coroutines.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SequentialNetworkRequestCallbackViewModel(
    private val mockApi: CallbackMockApi = mockApi()
): BaseViewModel<UiState>() {

    private var getAndroidVersionCall: Call<List<AndroidVersion>>? = null
    private var getAndroidFeaturesCall: Call<VersionFeatures>? = null

    fun perform2NetworkRequests() {

        uiState.value = UiState.Loading

        getAndroidVersionCall = mockApi.getRecentAndroidVersions()

        getAndroidVersionCall!!.enqueue(object : Callback<List<AndroidVersion>> {
            override fun onFailure(call: Call<List<AndroidVersion>>, t: Throwable) {
                uiState.value = UiState.Error("Network request failure!!")
            }

            override fun onResponse(
                call: Call<List<AndroidVersion>>,
                response: Response<List<AndroidVersion>>
            ) {
                if(response.isSuccessful) {
                    val mostRecentAndroidVersion = response.body()!!.last()

                    getAndroidFeaturesCall = mockApi.getAndroidVersionFeatures(mostRecentAndroidVersion.apiLevel)
                    getAndroidFeaturesCall!!.enqueue(object : Callback<VersionFeatures> {
                        override fun onFailure(call: Call<VersionFeatures>, t: Throwable) {
                            uiState.value = UiState.Error("Network Request failed")
                        }

                        override fun onResponse(
                            call: Call<VersionFeatures>,
                            response: Response<VersionFeatures>
                        ) {
                            if (response.isSuccessful) {
                                val featuresOfMostRecentVersion = response.body()!!
                                uiState.value = UiState.Success(featuresOfMostRecentVersion)
                            } else {
                                uiState.value = UiState.Error("Network Request failed")
                            }
                        }

                    })
                } else {
                    uiState.value = UiState.Error("Network Request failed")
                }
            }

        })
    }
}