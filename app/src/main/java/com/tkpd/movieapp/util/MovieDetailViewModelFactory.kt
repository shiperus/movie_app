package com.tkpd.movieapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tkpd.movieapp.datasource.repository.MovieDetailRepositoryImpl
import com.tkpd.movieapp.feature.moviedetail.domain.GetMovieDetailUseCase
import com.tkpd.movieapp.feature.moviedetail.view.MovieDetailViewModel

class MovieDetailViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(GetMovieDetailUseCase(MovieDetailRepositoryImpl())) as T
    }
}