package com.example.plisfunciona.repositorio

import com.example.plisfunciona.api.SpotifyAPI
import com.example.plisfunciona.modelo.Artist


class SpotifyRepository @Inject constructor(
    private val apiService: SpotifyAPI
) {
    // Obtiene las playlists del usuario
    suspend fun getUserPlaylists(): PlaylistResponse {
        return apiService.getUserPlaylists()
    }

    // Busca contenido en Spotify (canciones, artistas, playlists)
    suspend fun search(query: String): SearchResponse {
        return apiService.search(query)
    }

    // Obtiene detalles de un artista
    suspend fun getArtist(artistId: String): Artist {
        return apiService.getArtist(artistId)
    }

    // Obtiene las canciones más populares de un artista
    suspend fun getArtistTopTracks(artistId: String): TracksResponse {
        return apiService.getArtistTopTracks(artistId)
    }
}