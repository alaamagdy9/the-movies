package com.nweave.movies.data.model

// One official genre from TMDB. The endpoint returns nothing else - there is
// no image of any kind on it.
data class Genre(
    val id: Int,
    val name: String
)
