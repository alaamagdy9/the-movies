package com.nweave.movies.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nweave.movies.ui.components.PlaceholderScreen
import com.nweave.movies.ui.theme.MoviesTheme

@Composable
fun SearchScreen() {
    PlaceholderScreen("Search")
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MoviesTheme {
        SearchScreen()
    }
}