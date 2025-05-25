package com.example.plisfunciona.modelo

data class SearchResponse(
    val tracks: Tracks?,
    val artists: Artists?,
    val playlists: Playlists?
)

data class Tracks(
    val items: List<Track>
)

data class Artists(
    val items: List<Artist>
)

data class Playlists(
    val items: List<Playlist>
)