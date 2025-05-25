package com.example.plisfunciona.api

import okhttp3.OkHttpClient
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.plisfunciona.api.SpotifyAPI

object Retrofit{
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

    // Función para obtener el token de acceso (simplificado)
    private fun getAccessToken(): String {
        // En una app real, deberías implementar el flujo OAuth2 completo
        return "TU_TOKEN_DE_ACCESO"
    }

    private fun getAccessToken(): String {
        // Usa BuildConfig para acceder a las variables
        val clientId = BuildConfig.SPOTIFY_CLIENT_ID
        val clientSecret = BuildConfig.SPOTIFY_CLIENT_SECRET

        // Implementa OAuth2 (ejemplo simplificado)
        return obtenerTokenDeSpotify(clientId, clientSecret) // Función que harás tú
    }
}