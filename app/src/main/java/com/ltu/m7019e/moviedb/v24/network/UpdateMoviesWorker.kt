import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.ltu.m7019e.moviedb.v24.MovieDBApplication

class UpdateMoviesWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val appContainer = (applicationContext as MovieDBApplication).container
        val movieRepository = appContainer.moviesRepository
        val currentTab = inputData.getString("currentTab") ?: "popular"

        return try {
            when (currentTab) {
                "top_rated" -> movieRepository.getTopRatedMovies()
                "popular" -> movieRepository.getPopularMovies()
                else -> throw IllegalArgumentException("Invalid tab")
            }
            // 결과를 데이터베이스에 저장하는 로직 필요
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

fun scheduleMovieUpdateWork(context: Context, currentTab: String) {
    val data = workDataOf("currentTab" to currentTab)

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val workRequest = OneTimeWorkRequestBuilder<UpdateMoviesWorker>()
        .setInputData(data)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "updateMovies",
        ExistingWorkPolicy.APPEND_OR_REPLACE,
        workRequest
    )
}

