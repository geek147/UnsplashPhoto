package com.envious.domain.model

data class Photo(
    val description: String,
    val id: String,
    val blurHash: String,
    val urls: Urls,
    val username: String,
) {
    data class Urls(
        val full: String,
        val raw: String,
        val regular: String,
        val small: String,
        val thumb: String
    )
}
