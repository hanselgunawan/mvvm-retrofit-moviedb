package com.hanseltritama.moviedb.data.api

import com.hanseltritama.moviedb.data.MovieDetailsData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDBInterface {
    // Base URL --> https://api.themoviedb.org/3/
    // Popular --> https://api.themoviedb.org/3/movie/popular?api_key=1b6bccfa81825771cd296691cf2de775
    // Details --> https://api.themoviedb.org/3/movie/567189?api_key=1b6bccfa81825771cd296691cf2de775

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetailsData>
}