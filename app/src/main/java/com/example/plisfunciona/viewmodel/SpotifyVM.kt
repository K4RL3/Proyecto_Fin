package com.example.plisfunciona.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.plisfunciona.api.SpotifyAPI
import com.example.plisfunciona.modelo.Artist
import com.example.plisfunciona.modelo.Playlist
import com.example.plisfunciona.modelo.Track
import com.example.plisfunciona.modelo.SearchResponse

@HiltViewModel
class SpotifyVM @Inject constructor(
    private val api: SpotifyAPI
) : ViewModel() {

    // Estados para la pantalla
    // Home
    private val _recommendedPlaylists = MutableStateFlow<List<Playlist>>(emptyList())
    val recommendedPlaylists = _recommendedPlaylists.asStateFlow()
    private val _recentTracks = MutableStateFlow<List<Track>>(emptyList())
    val recentTracks = _recentTracks.asStateFlow()

    // Artist
    private val _currentArtist = MutableStateFlow<Artist?>(null)
    val currentArtist = _currentArtist.asStateFlow()
    private val _artistTopTracks = MutableStateFlow<List<Track>>(emptyList())
    val artistTopTracks = _artistTopTracks.asStateFlow()

    //  Search
    private val _searchResults = MutableStateFlow(SearchResults())
    val searchResults = _searchResults.asStateFlow()

    //  Playlist
    private val _userPlaylists = MutableStateFlow<List<Playlist>>(emptyList())
    val userPlaylists = _userPlaylists.asStateFlow()
    private val _currentPlaylist = MutableStateFlow<Playlist?>(null)
    val currentPlaylist = _currentPlaylist.asStateFlow()

    // Track
    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack = _currentTrack.asStateFlow()

    //manejo de errores
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                kotlinx.coroutines.async {
                    val playlists = api.getFeaturedPlaylists()
                    _recommendedPlaylists.value = playlists.playlists?.items ?: emptyList()
                }
                kotlinx.coroutines.async {
                    val recent = api.getRecentlyPlayed()
                    _recentTracks.value = recent.items?.map { it.track } ?: emptyList()
                }
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun searchContent(query: String) {
        viewModelScope.launch {
            if (query.length >= 2) {
                try {
                    val response = api.search(query)
                    _searchResults.value = SearchResults(
                        tracks = response.tracks?.items ?: emptyList(),
                        artists = response.artists?.items ?: emptyList()
                    )
                } catch (e: Exception) {
                    // Handle error
                }
            } else {
                _searchResults.value = SearchResults()
            }
        }
    }

    fun loadArtistDetails(artistId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                kotlinx.coroutines.coroutineScope {
                    val artistDeferred = kotlinx.coroutines.async { api.getArtist(artistId) }
                    val tracksDeferred = kotlinx.coroutines.async { api.getArtistTopTracks(artistId) }

                    _currentArtist.value = artistDeferred.await()
                    _artistTopTracks.value = tracksDeferred.await().tracks
                }
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun loadPlaylistDetails(playlistId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val playlist = api.getPlaylist(playlistId)
                _currentPlaylist.value = playlist
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleError(e: Exception) {
        val errorMessage = when (e) {
            is IOException -> "Error de conexión. Verifica tu internet."
            is HttpException -> "Error del servidor: ${e.code()}"
            else -> "Error inesperado: ${e.message}"
        }
        _uiState.value = UiState.Error(errorMessage)
    }

    sealed class UiState {
        object Initial : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }

    data class SearchResults(
        val tracks: List<Track> = emptyList(),
        val artists: List<Artist> = emptyList()
    )
}

