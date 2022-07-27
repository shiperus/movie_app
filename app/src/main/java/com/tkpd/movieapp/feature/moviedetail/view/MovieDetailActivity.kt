package com.tkpd.movieapp.feature.moviedetail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.tkpd.movieapp.databinding.ActivityMovieDetailBinding
import com.tkpd.movieapp.model.MovieDetail
import com.tkpd.movieapp.util.MovieDetailViewModelFactory
import com.tkpd.movieapp.util.Result.Error
import com.tkpd.movieapp.util.Result.Success
import com.tkpd.movieapp.util.hide
import com.tkpd.movieapp.util.loadImage
import com.tkpd.movieapp.util.show

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
        const val EXTRA_MOVIE_OVERVIEW = "extra_movie_overview"
        const val EXTRA_MOVIE_TITLE = "extra_movie_title"

    }

    private val viewModel: MovieDetailViewModel by viewModels(
        factoryProducer = { MovieDetailViewModelFactory() }
    )

    private var binding: ActivityMovieDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieId = intent.extras?.getInt(EXTRA_MOVIE_ID) ?: 0
        val movieOverview = intent.extras?.getString(EXTRA_MOVIE_OVERVIEW) ?: ""
        val movieTitle = intent.extras?.getString(EXTRA_MOVIE_TITLE) ?: ""

        binding = ActivityMovieDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
        setupActionBar()
        observeLiveData()
        showLoading()

        setMovieDetailTTFDListener()
        viewModel.getMovieDetail(movieId)
    }

    private fun setMovieDetailTTFDListener() {
        binding?.tvSynopsis?.viewTreeObserver?.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (binding?.tvSynopsis?.text?.isNotEmpty() == true) {
                        reportFullyDrawn()
                        hideLoading()
                        binding?.tvSynopsis?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                    }
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    private fun setupActionBar() {
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeLiveData() {
        viewModel.movieDetail.observe(this) {
            when (it) {
                is Success -> showMovieDetail(it.data)
                is Error -> onErrorGetMovieDetail(it.throwable)
                else -> showLoading()
            }
            hideLoading()
        }
    }

    private fun showMovieDetail(movie: MovieDetail) {
        binding?.apply {
            tvTitle.text = movie.title
            tvReleaseDate.text = movie.releaseDate
            tvGenre.text = movie.genres.joinToString(", ") { it.name }
            tvAvgVote.text = movie.voteAverage.toString()
            tvSynopsis.text = movie.overview
            ivCover.loadImage(movie.posterPath)
            tvReleaseDateLabel.show()
            tvAvgVoteLabel.show()
            tvGenreLabel.show()
        }
    }

    private fun onErrorGetMovieDetail(throwable: Throwable) {
        binding?.apply {
            showToasterError(throwable)
            tvReleaseDateLabel.hide()
            tvAvgVoteLabel.hide()
            tvGenreLabel.hide()
        }
    }

    private fun showToasterError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        binding?.progressBar?.show()
    }

    private fun hideLoading() {
        binding?.progressBar?.hide()
    }
}
