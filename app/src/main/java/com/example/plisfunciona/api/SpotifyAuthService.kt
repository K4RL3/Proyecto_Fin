package com.example.plisfunciona.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

// respuesta del token
data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)

interface SpotifyAuthApi {
    @FormUrlEncoded
    @POST("api/token")
    suspend fun getToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String
    ): TokenResponse
}

@Singleton
class SpotifyAuthService @Inject constructor() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://accounts.spotify.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val authApi = retrofit.create(SpotifyAuthApi::class.java)
    private var cachedToken: String? = null
    private var tokenExpireTime: Long = 0

    suspend fun getToken(): String {
        if (shouldRefreshToken()) {
            return refreshToken()
        }
        return cachedToken ?: refreshToken()
    }

    private fun shouldRefreshToken(): Boolean {
        return cachedToken == null || System.currentTimeMillis() > tokenExpireTime
    }

    private suspend fun refreshToken(): String {
        return try {
            val response = authApi.getToken(
                clientId = SpotifyAuth.CLIENT_ID,
                clientSecret = SpotifyAuth.CLIENT_SECRET,
                grantType = "client_credentials"
            )
            cachedToken = response.access_token
            tokenExpireTime = System.currentTimeMillis() + (response.expires_in * 1000)
            response.access_token
        } catch (e: Exception) {
            throw Exception("Error al obtener el token: ${e.message}")
        }
    }
}