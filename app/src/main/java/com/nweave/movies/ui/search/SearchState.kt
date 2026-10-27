package com.nweave.movies.ui.search

import com.nweave.movies.data.model.Movie
sealed interface SearchState {
    object Empty : SearchState
    object Loading : SearchState
    data class Success(val movies: List<Movie>) : SearchState
    data class Error(val message: String) : SearchState
}