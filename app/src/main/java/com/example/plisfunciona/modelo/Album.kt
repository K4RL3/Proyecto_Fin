package com.example.plisfunciona.modelo

data class Album(
    val id: String,
    val name: String,
    val images: List<Image>,
    val artists: List<Artist>
)