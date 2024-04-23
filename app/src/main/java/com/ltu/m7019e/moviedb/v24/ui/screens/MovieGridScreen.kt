package com.ltu.m7019e.moviedb.v24.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridCells.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ltu.m7019e.moviedb.v24.R
import com.ltu.m7019e.moviedb.v24.model.Movie
import com.ltu.m7019e.moviedb.v24.ui.theme.TheMovieDBV24Theme
import com.ltu.m7019e.moviedb.v24.utils.Constants
import com.ltu.m7019e.moviedb.v24.viewmodel.MovieListUiState

@Composable
fun MovieGridScreen(movieListUiState: MovieListUiState,
                    onMovieListItemClicked: (Movie) -> Unit,
                    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = Fixed(2),
//        verticalArrangement = Arrangement.spacedBy(dimensionResource(8)),
//        horizontalArrangement = Arrangement.spacedBy(dimensionResource(8)),
        modifier = modifier
    ) {

        when(movieListUiState) {
            is MovieListUiState.Success -> {
                items(movieListUiState.movies) { movie ->
                    MovieGridItemCard(
                        movie = movie,
                        onMovieListItemClicked,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            is MovieListUiState.Loading -> {
                item {
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            is MovieListUiState.Error -> {
                item {
                    Text(
                        text = "Error: Something went wrong!",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieGridItemCard(movie: Movie,
                      onMovieListItemClicked: (Movie) -> Unit,
                      modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(400.dp), // 카드의 높이를 250dp로 지정,
        onClick = {
            onMovieListItemClicked(movie)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally // 수직으로 중앙 정렬
        ) {
            Box {
                AsyncImage(
                    model = Constants.POSTER_IMAGE_BASE_URL + Constants.POSTER_IMAGE_WIDTH + movie.posterPath,
                    contentDescription = movie.title,
                    modifier = modifier
                        .fillMaxWidth() // 이미지의 너비를 가득 채우도록 설정
                        .aspectRatio(92f / 138f), // 이미지의 가로 세로 비율을 유지
                    contentScale = ContentScale.Crop
                )
            }
            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = movie.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GridPreview() {
    TheMovieDBV24Theme {
        MovieGridItemCard(
            movie = Movie(
                693134,
                "Dune: Part Two",
                "/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg",
                "/xOMo8BRK7PfcJv9JCnx7s5hj0PX.jpg",
                "2024-02-27",
                "Follow the mythic journey of Paul Atreides as he unites with Chani and the Fremen while on a path of revenge against the conspirators who destroyed his family. Facing a choice between the love of his life and the fate of the known universe, Paul endeavors to prevent a terrible future only he can foresee.",
            ), {}
        )
    }
}