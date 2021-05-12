package com.hanseltritama.moviedb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hanseltritama.moviedb.data.MovieDetailsData
import com.hanseltritama.moviedb.data.repository.MovieDetailsRepository
import com.hanseltritama.moviedb.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel(
    private val movieDetailsRepository: MovieDetailsRepository,
    movieId: Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetailsData> by lazy {
        movieDetailsRepository.fetchMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieDetailsRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose() // to prevent memory leaks
    }

}