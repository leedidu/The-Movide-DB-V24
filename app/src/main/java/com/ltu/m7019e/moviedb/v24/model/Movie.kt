package com.ltu.m7019e.moviedb.v24.model

import kotlinx.serialization.Serializable

data class Movie(
    var id: Long = 0L,
    var title: String,
    var posterPath: String,
    var backdropPath: String,
    var releaseDate: String,
    var overview: String,

    // add detail
    var adult: Boolean,
    var budget: String,
    var genres: List<String>,
    var homepage: String,
    var imdbId: String,
    var originalLanguage: String
)