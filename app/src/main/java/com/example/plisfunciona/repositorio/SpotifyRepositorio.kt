package com.example.plisfunciona.repositorio

import javax.inject.Inject
import com.example.plisfunciona.api.SpotifyAPI
import com.example.plisfunciona.modelo.*



class SpotifyRepository @Inject constructor(
    private val apiService: SpotifyAPI
) {
    // Obtiene las playlists del usuario
    suspend fun getUserPlaylists(): PlaylistResponse {
        return try {
            apiService.getUserPlaylists()
        } catch (e: Exception) {
            PlaylistResponse(Playlists(items = emptyList()))
        }
    }

    // Busca contenido en Spotify (canciones, artistas, playlists)
    suspend fun search(query: String): SearchResponse {
        return try {
            apiService.search(query)
        } catch (e: Exception) {
            SearchResponse(
                tracks = Tracks(items = emptyList()),
                artists = Artists(items = emptyList()),
                playlists = Playlists(items = emptyList())
            )
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
            TracksResponse(tracks = emptyList())
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

    // Obtiene detalles de una canción
    suspend fun getTrack(trackId: String): Track? {
        return try {
            apiService.getTrack(trackId)
        } catch (e: Exception) {
            null
        }
    }

    // Obtiene playlists recomendadas
    suspend fun getFeaturedPlaylists(): PlaylistResponse {
        return try {
            apiService.getRecommendedPlaylists()
        } catch (e: Exception) {
            PlaylistResponse(Playlists(items = emptyList()))
        }
    }

    suspend fun getRecentTracks(): TracksResponse {
        return try {
            apiService.getRecentTracks()
        } catch (e: Exception) {
            TracksResponse(tracks = emptyList())
        }
    }
}