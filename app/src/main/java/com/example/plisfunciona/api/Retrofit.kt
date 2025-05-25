package com.example.plisfunciona.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.plisfunciona.api.SpotifyAPI
import com.example.plisfunciona.api.SpotifyAuth

object Retrofit {
    private const val BASE_URL = "https://api.spotify.com/v1/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${getAccessToken()}")
                .build()
            chain.proceed(request)
        }
        .build()

    val api: SpotifyAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(SpotifyAPI::class.java)
    }

    private fun getAccessToken(): String {
        return SpotifyAuth.CLIENT_SECRET // Temporalmente, deberías implementar OAuth2
    }
}