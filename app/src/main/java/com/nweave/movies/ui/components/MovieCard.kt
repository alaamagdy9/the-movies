package com.nweave.movies.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.nweave.movies.data.model.Movie

// TMDB serves posters from its own image host; w500 is the width in pixels.
private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

// One cell of the trending grid: the poster, the rating over it, the title.
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieCard(movie: Movie, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column {
            Box {
                GlideImage(
                    // Not every film has a poster; null just draws nothing.
                    model = movie.posterPath?.let { path -> POSTER_BASE_URL + path },
                    contentDescription = movie.title,
                    // Posters are 2:3. The cell keeps that shape and the image
                    // fills it rather than being stretched out of proportion.
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f)
                )
                // The brief asks for the rating in the top-right corner, so it
                // sits on top of the poster rather than under the title.
                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                ) {
                    Text(
                        // TMDB sends things like 7.432; one decimal is enough.
                        text = "★ %.1f".format(movie.voteAverage),
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}