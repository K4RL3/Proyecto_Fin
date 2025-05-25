package com.example.plisfunciona.modelo

data class Artist(
    val id: String,
    val name: String,
    val images: List<Image> = emptyList(),
    val followers: Followers = Followers(0),
    val popularity: Int? = null
)

data class Image(
    val url: String,
    val height: Int? = null,
    val width: Int? = null
)

data class Followers(
    val total: Int
)