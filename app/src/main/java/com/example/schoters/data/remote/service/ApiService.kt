package com.example.schoters.data.remote.service

import com.example.schoters.data.remote.model.GetNewsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    // endpoint: everything
    @GET("everything")
    fun getNewsByKeyword(
        @Query("q") keyword: String,
        @Query("apiKey") apiKey: String
    ): Response<GetNewsResponse>
}