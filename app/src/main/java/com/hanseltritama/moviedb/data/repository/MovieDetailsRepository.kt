package com.hanseltritama.moviedb.data.repository

import androidx.lifecycle.LiveData
import com.hanseltritama.moviedb.data.MovieDetailsData
import com.hanseltritama.moviedb.data.api.MovieDBInterface
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: MovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetailsData> {
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}