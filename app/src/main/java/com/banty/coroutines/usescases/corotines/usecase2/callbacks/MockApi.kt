package com.banty.coroutines.usescases.corotines.usecase2.callbacks

import com.banty.coroutines.mock.AndroidVersion
import com.banty.coroutines.mock.VersionFeatures
import com.banty.coroutines.mock.mockAndroidVersions
import com.banty.coroutines.mock.mockVersionFeaturesAndroid10
import com.banty.coroutines.utils.MockNetworkInterceptor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

fun mockApi(): CallbackMockApi = createMockApi(
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

interface CallbackMockApi {

    @GET("recent-android-versions")
    fun getRecentAndroidVersions(): Call<List<AndroidVersion>>

    @GET("android-version-features/{apiLevel}")
    fun getAndroidVersionFeatures(@Path("apiLevel") apiLevel: Int): Call<VersionFeatures>
}

fun createMockApi(interceptor: MockNetworkInterceptor): CallbackMockApi {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(CallbackMockApi::class.java)
}