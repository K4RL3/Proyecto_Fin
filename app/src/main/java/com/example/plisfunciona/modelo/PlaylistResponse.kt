package com.example.plisfunciona.modelo

data class PlaylistResponse(
    val playlists: Playlists
)

data class Playlists(
    val items: List<Playlist>
)

