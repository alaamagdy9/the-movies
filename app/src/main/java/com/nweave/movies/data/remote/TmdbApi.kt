package com.nweave.movies.data.remote

import com.nweave.movies.data.model.GenresResponse
import com.nweave.movies.data.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    // The api_key is not a parameter here. An interceptor adds it to every
    // request instead, so no call site has to remember it.
    @GET("trending/movie/day")
    suspend fun getTrending(@Query("page") page: Int = 1): MovieListResponse

    // The official genre list. It takes no parameters and is not paged.
    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse

    // Search is a GET on TMDB, not a POST. The text the user typed goes in
    // the query parameter, and the response is the same envelope as trending.
    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): MovieListResponse
}
