package com.hanseltritama.moviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hanseltritama.moviedb.data.MovieDetailsData
import com.hanseltritama.moviedb.data.api.MovieDBClient
import com.hanseltritama.moviedb.data.api.MovieDBInterface
import com.hanseltritama.moviedb.data.api.POSTER_BASE_URL
import com.hanseltritama.moviedb.data.repository.MovieDetailsRepository
import com.hanseltritama.moviedb.data.repository.NetworkState
import com.hanseltritama.moviedb.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetails : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService: MovieDBInterface = MovieDBClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            movie_details_progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
        })
    }

    private fun getViewModel(movieId: Int): MovieDetailsViewModel {
        return ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailsViewModel(movieDetailsRepository, movieId) as T
            }
        })[MovieDetailsViewModel::class.java]
    }

    private fun bindUI(data: MovieDetailsData) {
        movie_details_title.text = data.title

        val moviePosterURL = POSTER_BASE_URL + data.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(movie_details_image)
    }
}