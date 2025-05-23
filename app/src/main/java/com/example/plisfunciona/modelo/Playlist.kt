package com.example.plisfunciona.modelo

data class Playlist(
    val id: String,
    val name: String,
    val images: List<Image>,
    val tracks: TracksInfo
)
