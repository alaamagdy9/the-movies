package com.nweave.movies.ui.genres

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nweave.movies.data.model.Genre
import com.nweave.movies.ui.theme.MoviesTheme

@Composable
fun GenresScreen(viewModel: GenresViewModel = hiltViewModel()) {
    // Subscribes to the ViewModel's StateFlow; every new value redraws this.
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val currentState = state) {
        GenresState.Loading -> Centered {
            CircularProgressIndicator()
        }

        is GenresState.Error -> Centered {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(currentState.message)
                Button(
                    onClick = viewModel::load,
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text("Try again")
                }
            }
        }

        is GenresState.Success -> GenresList(currentState.genres)
    }
}

// One genre per row, each taking the full width - the table view the brief
// asks for, rather than the grid the Trending tab uses.
@Composable
private fun GenresList(genres: List<Genre>) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(genres, key = { genre -> genre.id }) { genre ->
            GenreRow(genre)
        }
    }
}

// Only this screen shows a genre, so the row stays private here instead of
// becoming a shared component before anything else needs it.
@Composable
private fun GenreRow(genre: Genre) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = genre.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// Loading and Error both sit in the middle of an empty screen.
@Composable
private fun Centered(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

// The real screen needs a ViewModel, which a preview cannot build, so the
// preview draws the list with three invented genres instead.
@Preview(showBackground = true)
@Composable
private fun GenresListPreview() {
    MoviesTheme {
        GenresList(
            listOf(
                Genre(28, "Action"),
                Genre(35, "Comedy"),
                Genre(27, "Horror")
            )
        )
    }
}