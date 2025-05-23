package com.example.plisfunciona.modelo

import com.example.plisfunciona.modelo.Album

data class Track(
    val id: String,
    val name: String,
    val duration_ms: Int,
    val album: Album,
    val artists: List<Artist>,
    val preview_url: String?
)