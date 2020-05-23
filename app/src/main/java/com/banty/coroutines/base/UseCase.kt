package com.banty.coroutines.base

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.banty.coroutines.usescases.corotines.usecase1.PerformSingleNetworkRequestActivity
import com.banty.coroutines.usescases.corotines.usecase2.Perform2SequentialNetworkRequestsActivity
import com.banty.coroutines.usescases.corotines.usecase2.callbacks.SequentialNetworkRequestCallbackActivity
import com.banty.coroutines.usescases.corotines.usecase3.PerformNetworkRequestConcurrentActivity
import com.banty.coroutines.usescases.corotines.usecase4.NetworkRequestWithTimeoutActivity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UseCase(
    val description: String,
    val targetActivity: Class<out AppCompatActivity>
) : Parcelable

@Parcelize
data class UseCaseCategory(val categoryName: String, val useCases: List<UseCase>) : Parcelable

const val useCaseDescription1 = "#1 Perform single network request"
const val useCaseDescription2 = "#2 Perform two sequential network requests"
const val useCaseDescription3 = "#3 Perform several network requests concurrently"
const val useCase2UsingCallbacksDescription = "#2 using Callbacks"
const val useCaseDescription4 = "#4 network request with timeout"

private val coroutineUseCases =
    UseCaseCategory(
        "Coroutines Use Cases", listOf(
            UseCase(
                useCaseDescription1,
                PerformSingleNetworkRequestActivity::class.java
            ),
            UseCase(
                useCaseDescription2,
                Perform2SequentialNetworkRequestsActivity::class.java
            ),
            UseCase(
                useCase2UsingCallbacksDescription,
                SequentialNetworkRequestCallbackActivity::class.java
            ),
            UseCase(
                useCaseDescription3,
                PerformNetworkRequestConcurrentActivity::class.java
            ),
            UseCase(
                useCaseDescription4,
                NetworkRequestWithTimeoutActivity::class.java
            )
        )
    )


val useCaseCategories = listOf(
    coroutineUseCases
)