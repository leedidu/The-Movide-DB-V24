package com.ltu.m7019e.moviedb.v24

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ltu.m7019e.moviedb.v24.ui.screens.MovieDetailScreen
import com.ltu.m7019e.moviedb.v24.ui.screens.MovieGridScreen
import com.ltu.m7019e.moviedb.v24.ui.screens.MovieReviewScreen
import com.ltu.m7019e.moviedb.v24.viewmodel.MovieDBViewModel

enum class MovieDBScreen(@StringRes val title: Int) {
    List(title = R.string.app_name),
    Detail(title = R.string.movie_detail),
    Detail2(title = R.string.movie_detail2)
}


/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDBAppBar(
    currentScreen: MovieDBScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTabScreen(
    navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Popular", "Top Rated", "Favorite")
    val movieDBViewModel: MovieDBViewModel = viewModel(factory = MovieDBViewModel.Factory)

    Column {
        TopAppBar(title = { Text("Movie Categories") }
        )
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index  // Update the index directly
                        movieDBViewModel.selectedTabIndex = index  // Sync with ViewModel
                    },
                    text = { Text(title) }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> movieDBViewModel.getPopularMovies()
            1 -> movieDBViewModel.getTopRatedMovies()
            2 -> movieDBViewModel.getFavoriteMovies()
        }
    }
}

@Composable
fun MovieDBApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MovieDBScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieDBScreen.List.name
    )

    Scaffold(
        topBar = {
            MovieDBAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val movieDBViewModel: MovieDBViewModel = viewModel(factory = MovieDBViewModel.Factory)

        Column {
            if (currentScreen == MovieDBScreen.List) {
                MovieTabScreen(navController = navController)
            }
            NavHost(
                navController = navController,
                startDestination = MovieDBScreen.List.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(route = MovieDBScreen.List.name) {
                    MovieGridScreen(
                        movieListUiState = movieDBViewModel.movieListUiState,
                        onMovieListItemClicked = {
                            movieDBViewModel.setSelectedMovie(it)
                            movieDBViewModel.getDetails(it)
                            navController.navigate(MovieDBScreen.Detail.name)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
                composable(route = MovieDBScreen.Detail.name) {
                    MovieDetailScreen(
                        movieDBViewModel = movieDBViewModel,
                        selectedMovieDetailUiState = movieDBViewModel.detailUiState,
                        modifier = Modifier,
                        navController = navController,
                        onReviewDetailClicked = {
                            movieDBViewModel.getVideos(it)
                            movieDBViewModel.getReviews(it)
                            navController.navigate(MovieDBScreen.Detail2.name)
                        }
                    )
                }
                composable(route = MovieDBScreen.Detail2.name) {
                    MovieReviewScreen(
                        reviewUiState = movieDBViewModel.reivewUiState,
                        videoListUiState = movieDBViewModel.videoUiState
                    )
                }
            }
        }
    }
}