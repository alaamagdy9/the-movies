package com.nweave.movies.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.nweave.movies.R
import com.nweave.movies.data.model.Movie

private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185"

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    // What the user typed. The ViewModel never sees it until they search.
    var query by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { newText -> query = newText },
            label = { Text("Search movies") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { viewModel.search(query) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "Search"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        when (val currentState = state) {
            SearchState.Empty -> Centered {
                Text("Type a film name and press search.")
            }

            SearchState.Loading -> Centered {
                CircularProgressIndicator()
            }

            is SearchState.Error -> Centered {
                Text(currentState.message)
            }

            // An empty list is a successful search that matched nothing, which
            // is a different thing from an error.
            is SearchState.Success -> if (currentState.movies.isEmpty()) {
                Centered { Text("No films found for \"$query\".") }
            } else {
                ResultsList(currentState.movies)
            }
        }
    }
}

// The brief asks for a table view, so results are a full-width list rather
// than the grid the Trending tab uses.
@Composable
private fun ResultsList(movies: List<Movie>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies, key = { movie -> movie.id }) { movie ->
            MovieRow(movie)
        }
    }
}

// One result: a small poster beside the title and rating, laid out across the
// row rather than stacked as the grid cell does.
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieRow(movie: Movie) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            GlideImage(
                model = movie.posterPath?.let { path -> POSTER_BASE_URL + path },
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 60.dp, height = 90.dp)
            )
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "★ %.1f".format(movie.voteAverage),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun Centered(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}