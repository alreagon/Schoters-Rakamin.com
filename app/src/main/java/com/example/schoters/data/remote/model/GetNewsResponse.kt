package com.example.schoters.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetNewsResponse(
    @SerializedName("Articles")
    val articles: List<Article>?,
    @SerializedName("Status")
    val status: String?,
    @SerializedName("TotalREsults")
    val totalResults: Int?
) : Parcelable