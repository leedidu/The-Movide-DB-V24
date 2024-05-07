import android.util.Log
import com.ltu.m7019e.moviedb.v24.database.MovieDao
import com.ltu.m7019e.moviedb.v24.model.Details
import com.ltu.m7019e.moviedb.v24.model.Movie
import com.ltu.m7019e.moviedb.v24.model.MovieResponse
import com.ltu.m7019e.moviedb.v24.model.ReviewResponse
import com.ltu.m7019e.moviedb.v24.model.VideoResponse
import com.ltu.m7019e.moviedb.v24.network.MovieDBApiService

interface MoviesRepository {
    suspend fun getPopularMovies(): MovieResponse
    suspend fun getTopRatedMovies(): MovieResponse
    suspend fun getReviews(movieId : Long): ReviewResponse
    suspend fun getVideos(movieId: Long): VideoResponse
    suspend fun getDetails(movieId: Long): Details
}

class NetworkMoviesRepository(private val apiService: MovieDBApiService) : MoviesRepository {
    override suspend fun getPopularMovies(): MovieResponse {
        return apiService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): MovieResponse {
        return apiService.getTopRatedMovies()
    }
    override suspend fun getReviews(movieId: Long): ReviewResponse {
        return apiService.getReviews(movieId)
    }

    override suspend fun getVideos(movieId: Long): VideoResponse {
        return apiService.getVideos(movieId)
    }

    override suspend fun getDetails(movieId: Long): Details {
        return apiService.getDetails(movieId)
    }

}

interface SavedMovieRepository {
    suspend fun getSavedMovies(): List<Movie>

    suspend fun insertMovie(movie: Movie)

    suspend fun getMovie(id: Long): Movie

    suspend fun deleteMovie(movie: Movie)
    suspend fun getMoviesByTap(category: String): List<Movie>
    suspend fun deleteMoviesByTap(category: String)
    suspend fun insertLastTappedMovies(movies: List<Movie>, category: String)
    suspend fun getFavoriteMovies(): List<Movie>
}

class FavoriteMoviesRepository(private val movieDao: MovieDao) : SavedMovieRepository {
    override suspend fun getFavoriteMovies(): List<Movie> {
        return movieDao.getFavoriteMovies()
    }

    override suspend fun getSavedMovies(): List<Movie> {
        return movieDao.getMovies()
    }

    override suspend fun insertMovie(movie: Movie) {
        movieDao.insertFavoriteMovie(movie.id)
    }

    override suspend fun getMovie(id: Long): Movie {
        return movieDao.getMovie(id)
    }

    override suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteFavoriteMovie(movie.id)
    }

    override suspend fun getMoviesByTap(category: String): List<Movie> {
        return movieDao.getMoviesByCategory(category)
    }

    override suspend fun insertLastTappedMovies(movies: List<Movie>, category: String){
        movies.forEach { it.category = category }
        movieDao.insertMovies(movies)
    }

    override suspend fun deleteMoviesByTap(category: String) {
        movieDao.deleteMoviesByCategory(category)
    }
}