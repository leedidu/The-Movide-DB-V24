package com.ltu.m7019e.moviedb.v24.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ltu.m7019e.moviedb.v24.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE favorite = true")
    suspend fun getFavoriteMovies(): List<Movie>

    @Query("UPDATE movies SET favorite = true WHERE id = :id")
    suspend fun insertFavoriteMovie(id: Long)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovie(id: Long): Movie

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<Movie>

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteFavoriteMovie(id: Long)

    @Query("SELECT * FROM movies WHERE category = :category")
    suspend fun getMoviesByCategory(category: String): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM movies WHERE category = :category and favorite = false")
    suspend fun deleteMoviesByCategory(category: String)
}