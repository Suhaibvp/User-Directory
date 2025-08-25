package com.kenzo.userdictionary.ui.retrofit

import com.kenzo.userdictionary.ui.model.Post
import com.kenzo.userdictionary.ui.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/users")
    suspend fun getUsers(): Response<List<User>>
    @GET("/posts")
    suspend fun getPosts(@Query("userId") userId: Long): Response<List<Post>>
}