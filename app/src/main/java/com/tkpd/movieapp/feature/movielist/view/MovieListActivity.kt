package com.tkpd.movieapp.feature.movielist.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tkpd.movieapp.databinding.ActivityMovieListBinding
import com.tkpd.movieapp.feature.moviedetail.view.MovieDetailActivity
import com.tkpd.movieapp.feature.moviedetail.view.MovieDetailActivity.Companion.EXTRA_MOVIE_ID
import com.tkpd.movieapp.feature.movielist.adapter.MovieItemViewHolder
import com.tkpd.movieapp.feature.movielist.adapter.MovieListAdapter
import com.tkpd.movieapp.model.MovieItem
import com.tkpd.movieapp.model.PopularMovies
import com.tkpd.movieapp.util.MovieListViewModelFactory
import com.tkpd.movieapp.util.Result
import com.tkpd.movieapp.util.hide
import com.tkpd.movieapp.util.show

class MovieListActivity : AppCompatActivity(), MovieItemViewHolder.Listener {

    companion object {
        private const val GRID_SPAN_COUNT = 2
    }

    private lateinit var binding: ActivityMovieListBinding
    private var recyclerViewMovieList: RecyclerView? = null
    private var toolbar: Toolbar? = null
    private var progressBar: ProgressBar? = null
    private val viewModelFactory = MovieListViewModelFactory()
    private val viewModel: MovieListViewModel by viewModels(factoryProducer = { viewModelFactory })
    private val adapter: MovieListAdapter? by lazy {
        MovieListAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
        setSupportActionBar(toolbar)
        setupRecyclerView()
        getListMovie()
        observeLiveData()

    }

    private fun initView() {
        recyclerViewMovieList = binding.rvMovieList
        toolbar = binding.toolbar
        progressBar = binding.loadingSpinner
    }

    private fun getListMovie() {
        viewModel.getListPopularMovie()
    }

    private fun setupRecyclerView() {
        recyclerViewMovieList?.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
        recyclerViewMovieList?.adapter = adapter
    }

    private fun observeLiveData() {
        observePopularMovieData()
    }

    private fun observePopularMovieData() {
        viewModel.popularMovieData.observe(this) {
            hideLoading()
            when (it) {
                is Result.Success -> {
                    populateListMovie(it.data)
                }
                is Result.Error -> {
                    showToasterError(it.throwable)
                }
                else -> {
                    showLoading()
                }
            }
        }
    }

    private fun populateListMovie(popularMoviesData: PopularMovies) {
        adapter?.populateListMovie(popularMoviesData.movieItems)
    }

    private fun hideLoading() {
        progressBar?.hide()
    }

    private fun showLoading() {
        progressBar?.show()
    }

    private fun showToasterError(throwable: Throwable) {
        Toast.makeText(this, throwable.message.orEmpty(), Toast.LENGTH_LONG).show()
    }

    override fun onMovieItemClicked(movieItem: MovieItem) {
        redirectToMovieDetailPage(movieItem.id)
    }

    private fun redirectToMovieDetailPage(id: Int) {
        val intentMovieDetailPage = Intent(this, MovieDetailActivity::class.java)
        intentMovieDetailPage.putExtra(EXTRA_MOVIE_ID, id)
        startActivity(intentMovieDetailPage)
    }

}
