package com.example.plisfunciona.modelo

import com.example.plisfunciona.modelo.ArtistsResponse
import com.example.plisfunciona.modelo.TracksResponse
import com.example.plisfunciona.modelo.PlaylistResponse

data class SearchResponse(
    val tracks: TracksResponse,
    val artists: ArtistsResponse,
    val playlists: PlaylistResponse
)