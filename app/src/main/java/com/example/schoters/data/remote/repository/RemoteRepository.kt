package com.example.schoters.data.remote.repository

import com.example.schoters.data.remote.model.GetNewsResponse
import com.example.schoters.data.remote.service.ApiServices
import retrofit2.Response

class RemoteRepository(
    private val apiServices: ApiServices
) {
    fun getNews(keyword: String, apiKey: String): Response<GetNewsResponse> {
        return apiServices.getNewsByKeyword(keyword, apiKey)
    }
}