package com.nweave.movies.data.model

// The endpoint wraps the list in an object, so this is what Gson parses into.
data class GenresResponse(
    val genres: List<Genre>
)
