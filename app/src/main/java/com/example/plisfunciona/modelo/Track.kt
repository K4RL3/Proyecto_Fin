package com.example.plisfunciona.modelo

data class Track(
    val id: String,           // Agregar esta propiedad
    val name: String,
    val artists: List<Artist>,
    val album: Album
)