package com.ltu.m7019e.moviedb.v24.viewmodel

import MoviesRepository
import SavedMovieRepository
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ltu.m7019e.moviedb.v24.MovieDBApplication
import com.ltu.m7019e.moviedb.v24.model.Details
import com.ltu.m7019e.moviedb.v24.model.Movie
import com.ltu.m7019e.moviedb.v24.model.Review
import com.ltu.m7019e.moviedb.v24.model.Video
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MovieListUiState {
    data class Success(val movies: List<Movie>) : MovieListUiState
    object Error : MovieListUiState
    object Loading : MovieListUiState
}

sealed interface ReviewListUiState{
    data class Success(val reviews: List<Review>) : ReviewListUiState
    object Error : ReviewListUiState
    object Loading : ReviewListUiState
}

sealed interface SelectedMovieUiState {
    data class Success(val movie: Movie, val isFavorite: Boolean) : SelectedMovieUiState
    object Error : SelectedMovieUiState
    object Loading : SelectedMovieUiState
}

sealed interface VideoListUiState {
    data class Success(val video: List<Video>) : VideoListUiState
    object Error : VideoListUiState
    object Loading : VideoListUiState
}

sealed interface DetailUiState {
    data class Success(val detail: Details) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

class MovieDBViewModel(private val moviesRepository: MoviesRepository, private val savedMovieRepository: SavedMovieRepository) : ViewModel() {

    var movieListUiState: MovieListUiState by mutableStateOf(MovieListUiState.Loading)
        private set

    var selectedMovieUiState: SelectedMovieUiState by mutableStateOf(SelectedMovieUiState.Loading)
        private set

    var reivewUiState: ReviewListUiState by mutableStateOf(ReviewListUiState.Loading)
        private set

    var videoUiState: VideoListUiState by mutableStateOf(VideoListUiState.Loading)
        private set

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    init {
        getPopularMovies()
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(moviesRepository.getTopRatedMovies().results)
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(moviesRepository.getPopularMovies().results)
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun getReviews(movie: Movie) {
        viewModelScope.launch {
            reivewUiState = ReviewListUiState.Loading // movieReviewUiState로 업데이트
            reivewUiState = try {
                ReviewListUiState.Success(moviesRepository.getReviews(movie.id).results) // ReviewListUiState로 업데이트
            } catch (e: IOException) {
                ReviewListUiState.Error
            } catch (e: HttpException) {
                ReviewListUiState.Error
            }
        }
    }

    fun getVideos(movie: Movie){
        viewModelScope.launch {
            videoUiState = VideoListUiState.Loading
            videoUiState = try {
                Log.d("확인", movie.id.toString())
                VideoListUiState.Success(moviesRepository.getVideos(movie.id).results) // ReviewListUiState로 업데이트
            } catch (e: IOException) {
                VideoListUiState.Error
            } catch (e: HttpException) {
                VideoListUiState.Error
            }
        }
    }

    fun getDetails(movie: Movie){
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            detailUiState = try {
//                DetailUiState.Success(moviesRepository.getDetails(movie.id).homepage, moviesRepository.getDetails(movie.id).adult, moviesRepository.getDetails(movie.id).budget, moviesRepository.getDetails(movie.id).genres, moviesRepository.getDetails(movie.id).imdbId) // ReviewListUiState로 업데이트
                DetailUiState.Success(moviesRepository.getDetails(movie.id))
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }

    fun getSavedMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(savedMovieRepository.getSavedMovies())
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun saveMovie(movie: Movie) {
        viewModelScope.launch {
            savedMovieRepository.insertMovie(movie)
            selectedMovieUiState = SelectedMovieUiState.Success(movie, true)
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            savedMovieRepository.deleteMovie(movie)
            selectedMovieUiState = SelectedMovieUiState.Success(movie, false)
        }
    }

    fun setSelectedMovie(movie: Movie) {
        viewModelScope.launch {
            selectedMovieUiState = SelectedMovieUiState.Loading
            selectedMovieUiState = try {
                SelectedMovieUiState.Success(movie, savedMovieRepository.getMovie(movie.id) != null)
            } catch (e: IOException) {
                SelectedMovieUiState.Error
            } catch (e: HttpException) {
                SelectedMovieUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieDBApplication)
                val moviesRepository = application.container.moviesRepository
                val savedMovieRepository = application.container.savedMovieRepository
                MovieDBViewModel(moviesRepository = moviesRepository, savedMovieRepository = savedMovieRepository)
            }
        }
    }
}