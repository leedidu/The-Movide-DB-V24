package com.ltu.m7019e.moviedb.v24.database

import MoviesRepository
import NetworkMoviesRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ltu.m7019e.moviedb.v24.network.MovieDBApiService
import com.ltu.m7019e.moviedb.v24.utils.Constants
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val moviesRepository: MoviesRepository
}

class DefaultAppContainer : AppContainer {

    fun getLoggerInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    val movieDBJson = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(
            okhttp3.OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer ${Constants.API_KEY}")
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .addInterceptor(getLoggerInterceptor())
                .connectTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(movieDBJson.asConverterFactory("application/json".toMediaType()))
        .baseUrl(Constants.MOVIE_LIST_BASE_URL)
        .build()


    private val retrofitService: MovieDBApiService by lazy {
        retrofit.create(MovieDBApiService::class.java)
    }

    override val moviesRepository: MoviesRepository by lazy {
        NetworkMoviesRepository(retrofitService)
    }
}