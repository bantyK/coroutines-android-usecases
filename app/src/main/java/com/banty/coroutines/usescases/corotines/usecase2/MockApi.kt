package com.banty.coroutines.usescases.corotines.usecase2

import com.banty.coroutines.mock.createMockApi
import com.banty.coroutines.mock.mockAndroidVersions
import com.banty.coroutines.mock.mockVersionFeaturesAndroid10
import com.banty.coroutines.utils.MockNetworkInterceptor
import com.google.gson.Gson

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            Gson().toJson(mockAndroidVersions),
            200,
            1500
        )
        .mock(
            "http://localhost/android-version-features/29",
            Gson().toJson(mockVersionFeaturesAndroid10),
            200,
            1500
        )
)