package com.ltu.m7019e.moviedb.v24.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName(value = "id")
    var id: Int,

    @SerialName(value = "title")
    var title: String,

    @SerialName(value = "poster_path")
    var posterPath: String,

    @SerialName(value = "backdrop_path")
    var backdropPath: String,

    @SerialName(value = "release_date")
    var releaseDate: String,

    @SerialName(value = "overview")
    var overview: String,

//    @SerialName(value = "homepage")
//    var homepage: String? = null,
//
//    @SerialName(value = "imdb_id")
//    var imdbId: String? = null
) : List<Review> {
    override val size: Int
        get() = TODO("Not yet implemented")

    override fun contains(element: Review): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<Review>): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): Review {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: Review): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<Review> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: Review): Int {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<Review> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): ListIterator<Review> {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<Review> {
        TODO("Not yet implemented")
    }
}