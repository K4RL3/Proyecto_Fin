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
    private val repository: SpotifyRepository // Cambiar esto
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

    init {
        loadPlaylists()
    }

    private fun loadPlaylists() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getUserPlaylists()  // Usar repository en lugar de api
                _playlists.value = response.playlists?.items ?: emptyList()
            } catch (e: Exception) {
                // Manejo simple de errores
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

    data class SearchResults(
        val tracks: List<Track> = emptyList(),
        val artists: List<Artist> = emptyList()
    )
}

