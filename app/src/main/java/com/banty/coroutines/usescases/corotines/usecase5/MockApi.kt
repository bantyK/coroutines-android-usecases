package com.banty.coroutines.usescases.corotines.usecase5

import com.banty.coroutines.mock.*
import com.banty.coroutines.utils.MockNetworkInterceptor
import com.google.gson.Gson

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            Gson().toJson(mockAndroidVersions),
            200,
            1000
        )
        .mock(
            "http://localhost/android-version-features/27",
            Gson().toJson(mockVersionFeaturesOreo),
            MockNetworkInterceptor.INTERNAL_SERVER_ERROR_HTTP_CODE,
            100
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