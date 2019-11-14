package com.catherine.kotlinboilerplate.httpClient

import com.catherine.kotlinboilerplate.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Bill Pugh Singleton
class HttpClient private constructor() {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .build()


    val rallyCodingBaseRequest: Retrofit
        get() {
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Domains.RALLY_CODING_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    val jsonPlaceholderRequest: Retrofit
        get() {
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Domains.JSON_PLACEHOLDER_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    companion object {
        val instance = HttpClientHolder.instance
    }

    private object HttpClientHolder {
        val instance = HttpClient()
    }
}