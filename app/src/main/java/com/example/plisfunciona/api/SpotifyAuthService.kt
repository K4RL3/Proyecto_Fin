package com.example.plisfunciona.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

class SpotifyAuthService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://accounts.spotify.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val authApi = retrofit.create(SpotifyAuthApi::class.java)

    suspend fun getToken(clientId: String, clientSecret: String): String {
        return try {
            val response = authApi.getToken(
                clientId = clientId,
                clientSecret = clientSecret,
                grantType = "client_credentials"
            )
            response.access_token
        } catch (e: Exception) {
            throw Exception("Error al obtener el token: ${e.message}")
        }
    }
}