package com.example.schoters.di

import android.content.Context
import com.example.schoters.data.remote.repository.RemoteRepository
import com.example.schoters.data.remote.service.ApiServices
import com.example.schoters.utils.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    val baseUrl = "https://newsapi.org/v2/"

    single { provideNetworkHelper(androidContext()) }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), baseUrl) }
    single { provideApiService(get()) }
    single { RemoteRepository(get()) }
}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() = run {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .connectTimeout(1,TimeUnit.MINUTES)
        .writeTimeout(1,TimeUnit.MINUTES)
        .readTimeout(1,TimeUnit.MINUTES)
        .addInterceptor(loggingInterceptor)
        .build()
}

private fun provideRetrofit (
    okHttpClient: OkHttpClient,
    BASE_URl: String
) : Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URl)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)