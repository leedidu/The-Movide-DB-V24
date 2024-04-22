package com.ltu.m7019e.moviedb.v24.ui.screens

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.ltu.m7019e.moviedb.v24.model.Movie
import com.ltu.m7019e.moviedb.v24.utils.Constants
import com.ltu.m7019e.moviedb.v24.viewmodel.SelectedMovieUiState

@Composable
fun MovieDetailScreen(
    selectedMovieUiState: SelectedMovieUiState,
    modifier: Modifier = Modifier,
//    onMovieDetailClicked: (Movie) -> Unit
) {
    when (selectedMovieUiState) {
        is SelectedMovieUiState.Success -> {
            Column(Modifier.width(IntrinsicSize.Max)) {
                Box(Modifier.fillMaxWidth().padding(0.dp)) {
                    AsyncImage(
                        model = Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + selectedMovieUiState.movie.backdropPath,
                        contentDescription = selectedMovieUiState.movie.title,
                        modifier = modifier,
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = selectedMovieUiState.movie.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = selectedMovieUiState.movie.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = selectedMovieUiState.movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.size(8.dp))
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                ){
//                    Button(onClick = { onMovieDetailClicked(selectedMovieUiState.movie) },
//                        modifier = Modifier.align(Alignment.Center)
//                    ) {
//                        Text(text = "More Detail")
//                    }
//                }
            }
        }
        is SelectedMovieUiState.Loading -> {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
        is SelectedMovieUiState.Error -> {
            Text(
                text = "Error...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

//@Composable
//fun WebsiteLink(movie: Movie) {
//    val context = LocalContext.current
//    ClickableText(
//        text = AnnotatedString("Go to website"),
//        onClick = {
//            val uri = Uri.parse(movie.homepage)
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            context.startActivity(intent)
//        },
//        style = TextStyle(textDecoration = TextDecoration.Underline)
//    )
//}
//
//@Composable
//fun IMDbLink(movie: Movie) {
//    val context = LocalContext.current
//    val imdbAppUrl = "imdb:///title/${movie.imdbId}/"
//    val imdbWebUrl = "https://www.imdb.com/title/${movie.imdbId}/"
//    ClickableText(
//        text = AnnotatedString("Open IMDB"),
//        onClick = {
//            try {
//                // check if installed
//                context.packageManager.getPackageInfo("com.imdb.mobile", 0)
//                val uri = Uri.parse(imdbAppUrl)
//                val intent = Intent(Intent.ACTION_VIEW, uri)
//                intent.`package` = "com.imdb.mobile" // IMDb 앱을 명시적으로 호출
//                context.startActivity(intent)
//            } catch (e: PackageManager.NameNotFoundException) {
//                // if not -> uri
//                val uri = Uri.parse(imdbWebUrl)
//                val intent = Intent(Intent.ACTION_VIEW, uri)
//                context.startActivity(intent)
//            }
//        },
//        style = TextStyle(textDecoration = TextDecoration.Underline)
//    )
//}
