package com.example.plisfunciona.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plisfunciona.api.SpotifyAPI
import com.example.plisfunciona.modelo.Artist
import com.example.plisfunciona.modelo.Playlist
import com.example.plisfunciona.modelo.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyVM @Inject constructor(
    private val api: SpotifyAPI
) : ViewModel() {

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists

    private val _artist = MutableStateFlow<Artist?>(null)
    val artist: StateFlow<Artist?> = _artist

    private val _topTracks = MutableStateFlow<List<Track>>(emptyList())
    val topTracks: StateFlow<List<Track>> = _topTracks

    private val _searchResults = MutableStateFlow<List<Track>>(emptyList())
    val searchResults: StateFlow<List<Track>> = _searchResults

    fun getUserPlaylists() {
        viewModelScope.launch {
            try {
                val response = api.getUserPlaylists()
                _playlists.value = response.items
            } catch (e: Exception) {
                // Manejar el error
                TODO()
            }
        }
    }

    fun getArtist(artistId: String) {
        viewModelScope.launch {
            try {
                val response = api.getArtist(artistId)
                _artist.value = response
            } catch (e: Exception) {
                // Manejar el error
                TODO()
            }
        }
    }

    fun getArtistTopTracks(artistId: String) {
        viewModelScope.launch {
            try {
                val response = api.getArtistTopTracks(artistId)
                _topTracks.value = response.tracks
            } catch (e: Exception) {
                // Manejar el error
                TODO()
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val response = api.search(query)
                _searchResults.value = response.tracks.items
            } catch (e: Exception) {
                // Manejar el error
                TODO()
            }
        }
    }
}

