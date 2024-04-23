package com.ltu.m7019e.moviedb.v24.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ltu.m7019e.moviedb.v24.model.Review
import com.ltu.m7019e.moviedb.v24.viewmodel.ReviewListUiState

@Composable
fun MovieReviewScreen(
    reviewUiState: ReviewListUiState,
    modifier: Modifier = Modifier,
) {
    LazyRow(modifier = modifier) {
        when (reviewUiState) {
            is ReviewListUiState.Success -> {
                items(reviewUiState.reviews) { review ->
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(8.dp)
                    ) {
                        ReviewListItemCard(
                            review = review
                        )
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewListItemCard(
    review: Review,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(color = Color.LightGray) // 박스 배경색
            .padding(16.dp)
    ) {
        Text(
            text = review.author,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = review.createdAt,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = review.content,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
