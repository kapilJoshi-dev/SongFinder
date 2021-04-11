package com.example.songfinder.models

import java.io.Serializable

data class Track(
    var id: Int? = null,
    val artist: String,
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String?,
    val streamable: String,
    val url: String
): Serializable