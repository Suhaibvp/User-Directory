package com.kenzo.userdictionary.ui.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
private const val Base_Url="https://jsonplaceholder.typicode.com"
    val instance: ApiService by lazy{
        Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}