package com.nweave.movies.ui.trending

import com.nweave.movies.data.model.Movie
sealed interface TrendingState {
    object Loading : TrendingState
    data class Success(val movies: List<Movie>) : TrendingState
    data class Error(val message: String) : TrendingState
}