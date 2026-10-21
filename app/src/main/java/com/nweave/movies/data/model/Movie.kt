package com.nweave.movies.data.model

import com.google.gson.annotations.SerializedName

// One film from the trending list. The task asks for the title, the poster
// and the rating; the id comes along because the list needs a stable key.
data class Movie(
    val id: Int,
    val title: String,
    // Not every film has a poster, so this can come back null.
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Double
)