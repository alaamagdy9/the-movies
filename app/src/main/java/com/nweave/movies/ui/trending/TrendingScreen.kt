package com.nweave.movies.ui.trending

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nweave.movies.ui.components.PlaceholderScreen
import com.nweave.movies.ui.theme.MoviesTheme

@Composable
fun TrendingScreen() {
    PlaceholderScreen("Trending")
}

@Preview(showBackground = true)
@Composable
fun TrendingScreenPreview() {
    MoviesTheme {
        TrendingScreen()
    }
}