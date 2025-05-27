package com.example.plisfunciona.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.plisfunciona.api.SpotifyAPI
import com.example.plisfunciona.modelo.*
import com.example.plisfunciona.repositorio.SpotifyRepository
@HiltViewModel
class SpotifyVM @Inject constructor(
    private val repository: SpotifyRepository
) : ViewModel() {

    // Estados principales
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists = _playlists.asStateFlow()

    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack = _currentTrack.asStateFlow()

    private val _currentArtist = MutableStateFlow<Artist?>(null)
    val currentArtist = _currentArtist.asStateFlow()

    private val _searchResults = MutableStateFlow(SearchResults())
    val searchResults = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _artisrTopTracks = MutableStateFlow<TracksResponse>(TracksResponse(emptyList()))
    val artisrTopTracks = _artisrTopTracks.asStateFlow()

    private val _recentTracks = MutableStateFlow<List<Track>>(emptyList())
    val recentTracks = _recentTracks.asStateFlow()

    private val _currentPlaylist = MutableStateFlow<Playlist?>(null)
    val currentPlaylist = _currentPlaylist.asStateFlow()

    private val _recommendedPlaylists = MutableStateFlow<List<Playlist>>(emptyList())
    val recommendedPlaylists = _recommendedPlaylists.asStateFlow()

    private val _artistTopTracks = MutableStateFlow<List<Track>>(emptyList())
    val artistTopTracks = _artistTopTracks.asStateFlow()

    init {
        loadPlaylists()
    }

    private fun loadPlaylists() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getUserPlaylists()  // Usar repository en lugar de api
                _playlists.value = response.playlists.items ?: emptyList()
            } catch (e: Exception) { }
            finally {
                _isLoading.value = false
            }
        }
    }

    fun loadRecentTracks() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getUserPlaylists()
                _playlists.value = response.playlists.items ?: emptyList()
            } catch (e: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Replace the empty implementation with this:
    fun recommendedPlaylists() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getFeaturedPlaylists()
                _recommendedPlaylists.value = response.playlists?.items ?: emptyList()
            } catch (e: Exception) {
                _recommendedPlaylists.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchContent(query: String) {
        viewModelScope.launch {
            if (query.length >= 2) {
                try {
                    _isLoading.value = true
                    val response = repository.search(query)  // Usar repository en lugar de api
                    _searchResults.value = SearchResults(
                        tracks = response.tracks?.items ?: emptyList(),
                        artists = response.artists?.items ?: emptyList()
                    )
                } catch (e: Exception) {
                    _searchResults.value = SearchResults()
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun loadArtist(artistId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _currentArtist.value = repository.getArtist(artistId)
            } catch (e: Exception) {
                _currentArtist.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
    // Fix the loadArtistTopTracks function
    fun loadArtistTopTracks(artistId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getArtistTopTracks(artistId)
                _artistTopTracks.value = response.tracks ?: emptyList()
            } catch (e: Exception) {
                _artistTopTracks.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadTrack(trackId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _currentTrack.value = repository.getTrack(trackId)
            } catch (e: Exception) {
                _currentTrack.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPlaylistDetails(playlistId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val playlist = repository.getPlaylist(playlistId)
                _currentPlaylist.value = playlist
            } catch (e: Exception) {
                _currentPlaylist.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    data class SearchResults(
        val tracks: List<Track> = emptyList(),
        val artists: List<Artist> = emptyList()
    )
}

