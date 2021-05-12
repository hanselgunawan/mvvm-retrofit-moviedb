package com.hanseltritama.moviedb.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hanseltritama.moviedb.data.MovieDetailsData
import com.hanseltritama.moviedb.data.api.MovieDBInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable) {
    // CompositeDisposable --> dispose API calls (RxJava Component)

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetailsData>()
    val downloadedMovieResponse: LiveData<MovieDetailsData>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            it.message?.let { errorMessage -> Log.e("MovieDetailsData", errorMessage) }
                        }
                    )
            )
        } catch (e: Exception) {
            Log.e("MovieDetailsData", e.message.toString())
        }
    }
}