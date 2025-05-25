package com.example.plisfunciona.repositorio

import javax.inject.Inject
import com.example.plisfunciona.api.SpotifyAPI
import com.example.plisfunciona.modelo.Artist
import com.example.plisfunciona.modelo.Playlist
import com.example.plisfunciona.modelo.PlaylistResponse
import com.example.plisfunciona.modelo.SearchResponse
import com.example.plisfunciona.modelo.TracksResponse


class SpotifyRepository @Inject constructor(
    private val apiService: SpotifyAPI
) {
    // Obtiene las playlists del usuario
    suspend fun getUserPlaylists(): PlaylistResponse {
        return try {
            apiService.getUserPlaylists()
        } catch (e: Exception) {
            PlaylistResponse(emptyList())
        }
    }

    // Busca contenido en Spotify (canciones, artistas, playlists)
    suspend fun search(query: String): SearchResponse {
        return try {
            apiService.search(query)
        } catch (e: Exception) {
            SearchResponse(null, null, null)
        }
    }

    // Obtiene detalles de un artista
    suspend fun getArtist(artistId: String): Artist? {
        return try {
            apiService.getArtist(artistId)
        } catch (e: Exception) {
            null
        }
    }

    // Obtiene las canciones más populares de un artista
    suspend fun getArtistTopTracks(artistId: String): TracksResponse {
        return try {
            apiService.getArtistTopTracks(artistId)
        } catch (e: Exception) {
            TracksResponse(emptyList())
        }
    }

    // Obtiene detalles de una playlist
    suspend fun getPlaylist(playlistId: String): Playlist? {
        return try {
            apiService.getPlaylist(playlistId)
        } catch (e: Exception) {
            null
        }
    }
}