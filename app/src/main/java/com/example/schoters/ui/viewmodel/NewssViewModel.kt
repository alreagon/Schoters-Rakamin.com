package com.example.schoters.ui.viewmodel

import androidx.lifecycle.*
import com.example.schoters.data.remote.model.GetNewsResponse
import com.example.schoters.data.remote.repository.RemoteRepository
import com.example.schoters.utils.NetworkHelper
import com.example.schoters.utils.Resource
import kotlinx.coroutines.launch

class NewssViewModel(

    private val remoteRepository: RemoteRepository,
    private val networkHelper: NetworkHelper

) : ViewModel() {

    private val _getNews = MutableLiveData<Resource<GetNewsResponse>>()
    val getNews: LiveData<Resource<GetNewsResponse>>
        get() = _getNews

    fun getNewss(keyword: String, apiKey : String) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    val response = remoteRepository.getNews(keyword, apiKey)
                    val codeResponse = response.code()

                    if (response.isSuccessful) {
                        val getnewww = remoteRepository.getNews(keyword, apiKey)
                        _getNews.postValue(Resource.success(getnewww.body()))
                    } else {
                        _getNews.postValue(Resource.error("failed to get data banner",null))
                    }
                } catch (e: Exception) {
                    _getNews.postValue(Resource.error(e.message.toString(),null))
                }
            } else {
                _getNews.postValue(Resource.error("Error in getNews",null))
            }
        }
    }
















}