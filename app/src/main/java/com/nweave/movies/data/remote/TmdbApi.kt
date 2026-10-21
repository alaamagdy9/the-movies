package com.nweave.movies.data.remote

import com.nweave.movies.data.model.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    // The api_key is not a parameter here. An interceptor adds it to every
    // request instead, so no call site has to remember it.
    @GET("trending/movie/day")
    suspend fun getTrending(@Query("page") page: Int = 1): TrendingResponse
}