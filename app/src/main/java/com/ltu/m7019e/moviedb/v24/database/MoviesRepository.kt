import android.util.Log
import com.ltu.m7019e.moviedb.v24.model.Details
import com.ltu.m7019e.moviedb.v24.model.DetailsResponse
import com.ltu.m7019e.moviedb.v24.model.MovieResponse
import com.ltu.m7019e.moviedb.v24.model.ReviewResponse
import com.ltu.m7019e.moviedb.v24.model.Video
import com.ltu.m7019e.moviedb.v24.model.VideoResponse
import com.ltu.m7019e.moviedb.v24.network.MovieDBApiService

interface MoviesRepository {
    suspend fun getPopularMovies(): MovieResponse
    suspend fun getTopRatedMovies(): MovieResponse
    suspend fun getReviews(movieId : Int): ReviewResponse
    suspend fun getVideos(movieId: Int): VideoResponse
    suspend fun getDetails(movieId: Int): Details
}

class NetworkMoviesRepository(private val apiService: MovieDBApiService) : MoviesRepository {
    override suspend fun getPopularMovies(): MovieResponse {
        return apiService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): MovieResponse {
        return apiService.getTopRatedMovies()
    }

    override suspend fun getReviews(movieId : Int): ReviewResponse {
        return apiService.getReviews(movieId)
    }

    override suspend fun getVideos(movieId: Int): VideoResponse {
        return apiService.getVideos(movieId)
    }

    override suspend fun getDetails(movieId: Int): Details {
        return apiService.getDetails(movieId)
    }
}
