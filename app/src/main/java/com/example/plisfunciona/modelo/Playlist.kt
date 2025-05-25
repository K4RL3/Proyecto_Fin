package com.example.plisfunciona.modelo

data class Playlist(
    val id: String,
    val name: String,
    val description: String?,
    val images: List<Image>?,
    val tracks: PlaylistTracks?
)

data class PlaylistTracks(
    val items: List<PlaylistTrack>
)

data class PlaylistTrack(
    val track: Track
)