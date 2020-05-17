package com.banty.coroutines.base

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.banty.coroutines.usescases.corotines.usecase1.PerformSingleNetworkRequestActivity
import com.banty.coroutines.usescases.corotines.usecase2.Perform2SequentialNetworkRequests
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UseCase (
    val description:String,
    val targetActivity: Class<out AppCompatActivity>
): Parcelable

@Parcelize
data class UseCaseCategory(val categoryName: String, val useCases: List<UseCase>) : Parcelable

const val useCaseDescription1 = "#1 Perform single network request"
const val useCaseDescription2 = "#2 Perform two sequential network requests"

private val coroutineUseCases =
    UseCaseCategory(
        "Coroutines Use Cases", listOf(
            UseCase(
                useCaseDescription1,
                PerformSingleNetworkRequestActivity::class.java
            ),
            UseCase(
                useCaseDescription2,
                Perform2SequentialNetworkRequests::class.java
            )
        )
    )


val useCaseCategories = listOf(
    coroutineUseCases
)