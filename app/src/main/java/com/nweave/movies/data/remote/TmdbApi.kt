package com.nweave.movies.data.remote

import com.nweave.movies.data.model.GenresResponse
import com.nweave.movies.data.model.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
   //Interceptor Used
    @GET("trending/movie/day")
    suspend fun getTrending(@Query("page") page: Int = 1): TrendingResponse

    // The official genre list. It takes no parameters and is not paged.
    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse
}