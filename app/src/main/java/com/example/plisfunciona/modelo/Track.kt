package com.example.plisfunciona.modelo

data class Track(
    val id: String,
    val name: String,
    val artists: List<Artist>,
    val album: Album
)