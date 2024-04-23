package com.ltu.m7019e.moviedb.v24.network

import com.ltu.m7019e.moviedb.v24.model.MovieResponse
import com.ltu.m7019e.moviedb.v24.model.ReviewResponse
import com.ltu.m7019e.moviedb.v24.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApiService {

    @GET("popular")
    suspend fun getPopularMovies(
    ): MovieResponse

    @GET("top_rated")
    suspend fun getTopRatedMovies(
    ): MovieResponse

    @GET("{movie-id}/reviews")
    suspend fun getReviews(
        @Path("movie-id")
        movieId: Int
    ): ReviewResponse
}