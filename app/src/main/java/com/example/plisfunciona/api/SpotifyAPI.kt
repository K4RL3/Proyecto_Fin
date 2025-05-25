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

// Primero, crea una interfaz para los endpoints
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

// Luego, crea la clase SpotifyAPI que utiliza la interfaz
class SpotifyAPI {
    private val BASE_URL = "https://api.spotify.com/v1/"
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val spotifyService = retrofit.create(SpotifyService::class.java)

    // Métodos para acceder a los endpoints
    suspend fun getUserPlaylists() = spotifyService.getUserPlaylists()
    suspend fun search(query: String, type: String = "track,artist,playlist", limit: Int = 20) = 
        spotifyService.search(query, type, limit)
    suspend fun getArtist(id: String) = spotifyService.getArtist(id)
    suspend fun getArtistTopTracks(id: String) = spotifyService.getArtistTopTracks(id)
    suspend fun getRecommendedPlaylists() = spotifyService.getRecommendedPlaylists()
    suspend fun getRecentTracks() = spotifyService.getRecentTracks()
}