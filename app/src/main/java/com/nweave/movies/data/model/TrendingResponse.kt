package com.nweave.movies.data.model

import com.google.gson.annotations.SerializedName
data class TrendingResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int
)