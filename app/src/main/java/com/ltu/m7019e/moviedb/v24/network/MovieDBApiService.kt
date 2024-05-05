package com.ltu.m7019e.moviedb.v24.network

import com.ltu.m7019e.moviedb.v24.model.Details
import com.ltu.m7019e.moviedb.v24.model.DetailsResponse
import com.ltu.m7019e.moviedb.v24.model.MovieResponse
import com.ltu.m7019e.moviedb.v24.model.ReviewResponse
import com.ltu.m7019e.moviedb.v24.model.Video
import com.ltu.m7019e.moviedb.v24.model.VideoResponse
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

    @GET("{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id")
        movieId: Long
    ): ReviewResponse

    @GET("{movie_id}")
    suspend fun getDetails(
        @Path("movie_id")
        movieId: Long
    ): Details

    @GET("{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id")
        movieId: Long
    ): VideoResponse
}