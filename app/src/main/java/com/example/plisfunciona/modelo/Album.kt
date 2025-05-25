package com.example.plisfunciona.modelo

data class Album(
    val id: String,
    val name: String,
    val images: List<Image>,
    val artists: List<Artist>
)

data class ArtistsResponse(
    val items: List<Artist>
)


data class Playlist(
    val id: String,
    val name: String,
    val images: List<Image>,
    val tracks: TracksInfo
)

data class SearchResponse(
    val tracks: TracksResponse,
    val artists: ArtistsResponse,
    val playlists: PlaylistResponse
)

data class TracksInfo(
    val total: Int
)

