package com.nweave.movies.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nweave.movies.R

// The three tabs along the bottom, in the order they appear in the bar.
enum class MovieDestination(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    Trending("trending", R.string.tab_trending, R.drawable.ic_trending),
    Genres("genres", R.string.tab_genres, R.drawable.ic_genres),
    Search("search", R.string.tab_search, R.drawable.ic_search)
}