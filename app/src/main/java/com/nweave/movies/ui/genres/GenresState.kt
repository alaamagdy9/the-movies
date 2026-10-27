package com.nweave.movies.ui.genres
import com.nweave.movies.data.model.Genre
sealed interface GenresState {
    object Loading : GenresState
    data class Success(val genres: List<Genre>) : GenresState
    data class Error(val message: String) : GenresState
}
