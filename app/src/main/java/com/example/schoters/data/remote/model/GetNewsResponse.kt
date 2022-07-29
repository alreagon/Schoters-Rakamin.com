package com.example.schoters.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetNewsResponse(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
) : Parcelable