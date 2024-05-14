package com.ltu.m7019e.moviedb.v24

import UpdateMoviesWorker
import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ltu.m7019e.moviedb.v24.database.AppContainer
import com.ltu.m7019e.moviedb.v24.database.DefaultAppContainer
import java.util.concurrent.TimeUnit

class MovieDBApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val updateWorkRequest = PeriodicWorkRequestBuilder<UpdateMoviesWorker>(12, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "updateMovies",
            ExistingPeriodicWorkPolicy.KEEP,
            updateWorkRequest
        )
    }
}
