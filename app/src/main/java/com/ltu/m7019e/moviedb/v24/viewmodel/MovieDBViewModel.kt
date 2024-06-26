package com.ltu.m7019e.moviedb.v24.viewmodel

import MoviesRepository
import SavedMovieRepository
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.WorkerParameters
import com.ltu.m7019e.moviedb.v24.MovieDBApplication
import com.ltu.m7019e.moviedb.v24.database.AppContainer
import com.ltu.m7019e.moviedb.v24.model.Details
import com.ltu.m7019e.moviedb.v24.model.Movie
import com.ltu.m7019e.moviedb.v24.model.Review
import com.ltu.m7019e.moviedb.v24.model.Video
import com.ltu.m7019e.moviedb.v24.utils.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MovieListUiState {
    data class Success(val movies: List<Movie>) : MovieListUiState
    object Error : MovieListUiState
    object Loading : MovieListUiState
    object Empty : MovieListUiState
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

class MovieDBViewModel(
    private val context: Context,
    private val moviesRepository: MoviesRepository,
    private val favoriteMoviesRepository: SavedMovieRepository,
    private val savedMovieRepository: SavedMovieRepository,
) : ViewModel() {

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

    private val _networkAvailable = MutableStateFlow(true)

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndexFlow: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    var selectedTabIndex: Int
        get() = _selectedTabIndex.value
        set(value) {
            _selectedTabIndex.value = value
            refreshData()
        }

    init {
        observeNetworkConnectivity(context)
    }

    private fun observeNetworkConnectivity(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _networkAvailable.value = true
                refreshData()
            }

            override fun onLost(network: Network) {
                _networkAvailable.value = false
            }
        })
    }

    private fun refreshData() {
        when (_selectedTabIndex.value) {
            0 -> getPopularMovies()
            1 -> getTopRatedMovies()
            2 -> getFavoriteMovies()
        }
    }


    fun getTopRatedMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            try {
                val newMovies = moviesRepository.getTopRatedMovies().results
                val existingFavorites = favoriteMoviesRepository.getFavoriteMovies().map { it.id }.toSet()
                newMovies.forEach { movie ->
                    if (movie.id in existingFavorites) {
                        movie.favorite = true
                    }
                }
                savedMovieRepository.deleteMoviesByTap("popular")
                savedMovieRepository.insertLastTappedMovies(newMovies, "top_rated")
                movieListUiState = MovieListUiState.Success(newMovies)
            } catch (e: IOException) {
                val cachedMovies = favoriteMoviesRepository.getMoviesByTap("top_rated")
                movieListUiState = MovieListUiState.Success(cachedMovies)
            } catch (e: HttpException) {
                val cachedMovies = favoriteMoviesRepository.getMoviesByTap("top_rated")
                movieListUiState = MovieListUiState.Success(cachedMovies)
            }
        }
    }



    fun getPopularMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            try {
                val newMovies = moviesRepository.getPopularMovies().results
                val existingFavorites = favoriteMoviesRepository.getFavoriteMovies().map { it.id }.toSet()
                newMovies.forEach { movie ->
                    if (movie.id in existingFavorites) {
                        movie.favorite = true
                    }
                }
                savedMovieRepository.deleteMoviesByTap("top_rated")
                savedMovieRepository.insertLastTappedMovies(newMovies, "popular")
                movieListUiState = MovieListUiState.Success(newMovies)
            } catch (e: IOException) {
                val cachedMovies = favoriteMoviesRepository.getMoviesByTap("popular")
                movieListUiState = MovieListUiState.Success(cachedMovies)
            } catch (e: HttpException) {
                val cachedMovies = favoriteMoviesRepository.getMoviesByTap("popular")
                movieListUiState = MovieListUiState.Success(cachedMovies)
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
                VideoListUiState.Success(moviesRepository.getVideos(movie.id).results) // ReviewListUiState로 업데이트
            } catch (e: IOException) {
                VideoListUiState.Error
            } catch (e: HttpException) {
                VideoListUiState.Error
            }
        }
    }

    fun getDetails(movie: Movie) {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            try {
                val details = moviesRepository.getDetails(movie.id)
                val isFavorite = savedMovieRepository.getMovie(movie.id)?.favorite ?: false
                detailUiState = DetailUiState.Success(details)
                selectedMovieUiState = SelectedMovieUiState.Success(movie, isFavorite)
            } catch (e: Exception) {
                detailUiState = DetailUiState.Error
            }
        }
    }

    fun getFavoriteMovies() {
        viewModelScope.launch {
            try {
                val favoriteMovies = savedMovieRepository.getFavoriteMovies()
                movieListUiState = if (favoriteMovies.isEmpty()) {
                    MovieListUiState.Empty  // 비어있는 경우 Empty 상태 반환
                } else {
                    MovieListUiState.Success(favoriteMovies)  // 데이터가 있는 경우 Success 상태 반환
                }
            } catch (e: Exception) {
                movieListUiState = MovieListUiState.Error
            }
        }
    }

    fun saveMovie(movie: Movie) {
        viewModelScope.launch {
            movie.favorite = true
            savedMovieRepository.insertMovie(movie)
            selectedMovieUiState = SelectedMovieUiState.Success(movie, true)
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            movie.favorite = false
            savedMovieRepository.deleteMovie(movie)
            selectedMovieUiState = SelectedMovieUiState.Success(movie, false)
        }
    }

    fun getLastTapMovies(lastTap: String) {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                val cachedMovies = savedMovieRepository.getMoviesByTap(lastTap)
                if (cachedMovies.isNotEmpty()) {
                    MovieListUiState.Success(cachedMovies)
                } else {
                    MovieListUiState.Error
                }
            } catch (e: Exception) {
                MovieListUiState.Error
            }
        }
    }

    fun setSelectedMovie(movie: Movie) {
        viewModelScope.launch {
            selectedMovieUiState = SelectedMovieUiState.Loading
            try {
                val fetchedMovie = savedMovieRepository.getMovie(movie.id)
                selectedMovieUiState = SelectedMovieUiState.Success(movie, fetchedMovie?.favorite ?: false)
            } catch (e: Exception) {
                selectedMovieUiState = SelectedMovieUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieDBApplication)
                val moviesRepository = application.container.moviesRepository
                val favoriteMoviesRepository = application.container.savedMovieRepository
                val savedMovieRepository = application.container.savedMovieRepository
                // Correctly pass the application context
                MovieDBViewModel(application.applicationContext, moviesRepository, favoriteMoviesRepository, savedMovieRepository)
            }
        }
    }
}