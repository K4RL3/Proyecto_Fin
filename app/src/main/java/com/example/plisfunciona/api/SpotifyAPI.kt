package com.example.plisfunciona.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.plisfunciona.modelo.PlaylistResponse
import com.example.plisfunciona.modelo.SearchResponse
import com.example.plisfunciona.modelo.Artist
import com.example.plisfunciona.modelo.TracksResponse

interface SpotifyAPI {
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
}