package com.nweave.movies.ui.trending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nweave.movies.data.model.Movie
import com.nweave.movies.ui.components.MovieCard
import com.nweave.movies.ui.theme.MoviesTheme

@Composable
fun TrendingScreen(viewModel: TrendingViewModel = hiltViewModel()) {
    // Reads the ViewModel's state and redraws whenever it changes.
    val state by viewModel.state.collectAsStateWithLifecycle()

    // One of exactly three things, and the compiler checks all three are here.
    when (val currentState = state) {
        TrendingState.Loading -> Centered {
            CircularProgressIndicator()
        }

        is TrendingState.Error -> Centered {
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

        is TrendingState.Success -> TrendingGrid(currentState.movies)
    }
}

// Two columns, and only the cells on screen are built.
@Composable
private fun TrendingGrid(movies: List<Movie>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies, key = { movie -> movie.id }) { movie ->
            MovieCard(movie)
        }
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
// preview draws the grid with two invented films instead.
@Preview(showBackground = true)
@Composable
private fun TrendingGridPreview() {
    MoviesTheme {
        TrendingGrid(
            listOf(
                Movie(1, "Dune: Part Two", null, 8.2),
                Movie(2, "The Wild Robot", null, 8.4)
            )
        )
    }
}