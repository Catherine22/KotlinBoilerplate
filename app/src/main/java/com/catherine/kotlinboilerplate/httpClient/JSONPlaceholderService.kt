package com.catherine.kotlinboilerplate.httpClient

import com.catherine.kotlinboilerplate.data.Post
import retrofit2.Call
import retrofit2.http.GET

interface JSONPlaceholderService {
    @GET("photos")
    fun getPosts(): Call<List<Post>>
}