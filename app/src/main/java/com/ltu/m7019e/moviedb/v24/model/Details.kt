package com.ltu.m7019e.moviedb.v24.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Details(
    @SerialName(value = "adult")
    var adult: Boolean,

    @SerialName(value = "budget")
    var budget: Int,

    @SerialName(value = "genres")
    var genres: List<Genre>,

    @SerialName(value = "homepage")
    var homepage: String,

    @SerialName(value = "imdb_id")
    var imdbId: String?,
)

@Serializable
data class Genre(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)