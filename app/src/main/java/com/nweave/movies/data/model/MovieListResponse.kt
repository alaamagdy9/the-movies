package com.nweave.movies.data.model

import com.google.gson.annotations.SerializedName

// Several TMDB endpoints return the same envelope around a page of films -
// trending and search among them - so this is named for what it holds rather
// than for the first call that used it.
data class MovieListResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int
)
