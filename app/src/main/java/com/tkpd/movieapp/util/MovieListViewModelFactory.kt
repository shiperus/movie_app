package com.tkpd.movieapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tkpd.movieapp.datasource.repository.MovieListRepositoryImpl
import com.tkpd.movieapp.feature.movielist.domain.GetMovieListUseCase
import com.tkpd.movieapp.feature.movielist.view.MovieListViewModel

class MovieListViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieListViewModel(GetMovieListUseCase(MovieListRepositoryImpl())) as T
    }
}