package com.example.plisfunciona.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.plisfunciona.modelo.PlaylistResponse
import com.example.plisfunciona.modelo.SearchResponse
import com.example.plisfunciona.modelo.Artist
import com.example.plisfunciona.modelo.TracksResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import javax.inject.Inject

interface SpotifyService {
    @GET("me/playlists")
    suspend fun getUserPlaylists(): PlaylistResponse

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String = "track,artist,playlist",
        @Query("limit") limit: Int = 20
    ): SearchResponse

    @GET("artists/{id}")
    suspend fun getArtist(@Path("id") id: String): Artist

    @GET("artists/{id}/top-tracks")
    suspend fun getArtistTopTracks(@Path("id") id: String): TracksResponse

    @GET("browse/featured-playlists")
    suspend fun getRecommendedPlaylists(): PlaylistResponse

    @GET("me/player/recently-played")
    suspend fun getRecentTracks(): TracksResponse
}

class SpotifyAPI @Inject constructor(
    private val authService: SpotifyAuthService
) {
    private val BASE_URL = "https://api.spotify.com/v1/"
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${authService.getToken()}")
                .build()
            chain.proceed(request)
        }
        .build()

    private val spotifyService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SpotifyService::class.java)

    // Métodos para acceder a los endpoints
    suspend fun getUserPlaylists() = spotifyService.getUserPlaylists()
    suspend fun search(query: String, type: String = "track,artist,playlist", limit: Int = 20) = 
        spotifyService.search(query, type, limit)
    suspend fun getArtist(id: String) = spotifyService.getArtist(id)
    suspend fun getArtistTopTracks(id: String) = spotifyService.getArtistTopTracks(id)
    suspend fun getRecommendedPlaylists() = spotifyService.getRecommendedPlaylists()
    suspend fun getRecentTracks() = spotifyService.getRecentTracks()
}