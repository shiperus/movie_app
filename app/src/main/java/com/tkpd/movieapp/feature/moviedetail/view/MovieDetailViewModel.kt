package com.tkpd.movieapp.feature.moviedetail.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tkpd.movieapp.feature.moviedetail.domain.GetMovieDetailUseCase
import com.tkpd.movieapp.model.MovieDetail
import com.tkpd.movieapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    val movieDetail: LiveData<Result<MovieDetail>>
        get() = _movieDetail

    private val _movieDetail = MutableLiveData<Result<MovieDetail>>()

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movieListResponse = getMovieDetailUseCase.execute(movieId)
                _movieDetail.postValue(movieListResponse)
            } catch (e: Exception) {
                _movieDetail.postValue(Result.Error(e))
            }
        }
    }
}
