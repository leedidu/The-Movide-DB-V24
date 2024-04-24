package com.ltu.m7019e.moviedb.v24.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.ltu.m7019e.moviedb.v24.model.Review
import com.ltu.m7019e.moviedb.v24.model.Video
import com.ltu.m7019e.moviedb.v24.viewmodel.ReviewListUiState
import com.ltu.m7019e.moviedb.v24.viewmodel.VideoListUiState

@Composable
fun MovieReviewScreen(
    reviewUiState: ReviewListUiState,
    videoListUiState: VideoListUiState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        VideoList(videoListUiState)
        ReviewList(reviewUiState)
    }
}

@Composable
fun VideoList(videoListUiState: VideoListUiState) {
    LazyColumn {
        item {
            Text(
                text = "Video",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            LazyRow {
                when (videoListUiState) {
                    is VideoListUiState.Success -> {
                        items(videoListUiState.video) { video ->
                            Box(
                                modifier = Modifier
                                    .width(200.dp)
                                    .padding(8.dp)
                            ) {
                                VideoListItemCard(
                                    video = video
                                )
                            }
                        }
                    }
                    is VideoListUiState.Loading -> {
                        item {
                            Text(
                                text = "Loading...",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    is VideoListUiState.Error -> {
                        item {
                            Text(
                                text = "Error...",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ReviewList(reviewUiState: ReviewListUiState) {
    Column {
        Text(
            text = "Review",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            when (reviewUiState) {
                is ReviewListUiState.Success -> {
                    items(reviewUiState.reviews) { review ->
                        ReviewListItemCard(
                            review = review,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                is ReviewListUiState.Loading -> {
                    item {
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                is ReviewListUiState.Error -> {
                    item {
                        Text(
                            text = "Error...",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewListItemCard(review: Review, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .width(300.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val imageUrl = review.authorDetails.avatarPath?.let { "https://image.tmdb.org/t/p/w200/$it" } ?: "default_avatar_url"
                    Image(
                        painter = rememberImagePainter(data = imageUrl),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(review.author, fontWeight = FontWeight.Bold)
                        review.authorDetails.rating?.let { rating ->
                            Text("${rating}★", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(review.content, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(review.createdAt, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun VideoListItemCard(
    video: Video,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .padding(8.dp)
            .width(600.dp)
            .aspectRatio(16f / 9f)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp)
            .clickable {
                if (video.site == "YouTube") {
                    val youtubeUrl = "https://www.youtube.com/watch?v=${video.key}"
                    uriHandler.openUri(youtubeUrl)
                }
            }
    ) {
        if (video.site == "YouTube") {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberImagePainter(
                        "https://img.youtube.com/vi/${video.key}/maxresdefault.jpg"
                    ),
                    contentDescription = "YouTube Thumbnail",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text("Go to Youtube", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            val mediaSourceUri = "https://www.${video.site}.com/${video.key}"
            LaunchedEffect(mediaSourceUri) {
                val mediaItem = MediaItem.fromUri(mediaSourceUri)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
            }

            DisposableEffect(Unit) {
                onDispose {
                    exoPlayer.release()
                }
            }
        }
    }
}
