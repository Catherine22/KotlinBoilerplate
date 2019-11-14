package com.catherine.kotlinboilerplate.httpClient

import com.catherine.kotlinboilerplate.data.Album
import retrofit2.Call
import retrofit2.http.GET

interface RallyCodingService {
    @GET("music_albums")
    fun getAlbums(): Call<List<Album>>
}