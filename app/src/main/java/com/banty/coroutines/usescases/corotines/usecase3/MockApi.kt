package com.banty.coroutines.usescases.corotines.usecase3

import com.banty.coroutines.mock.createMockApi
import com.banty.coroutines.mock.mockVersionFeaturesAndroid10
import com.banty.coroutines.mock.mockVersionFeaturesOreo
import com.banty.coroutines.mock.mockVersionFeaturesPie
import com.banty.coroutines.utils.MockNetworkInterceptor
import com.google.gson.Gson

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/android-version-features/27",
            Gson().toJson(mockVersionFeaturesOreo),
            200,
            1000
        )
        .mock(
            "http://localhost/android-version-features/28",
            Gson().toJson(mockVersionFeaturesPie),
            200,
            1000
        )
        .mock(
            "http://localhost/android-version-features/29",
            Gson().toJson(mockVersionFeaturesAndroid10),
            200,
            1000
        )
)