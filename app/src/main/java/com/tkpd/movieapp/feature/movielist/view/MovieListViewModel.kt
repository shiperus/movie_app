package com.tkpd.movieapp.feature.movielist.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tkpd.movieapp.feature.movielist.domain.GetMovieListUseCase
import com.tkpd.movieapp.model.PopularMovies
import com.tkpd.movieapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieListViewModel(
    private val getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    private val _popularMovieData = MutableLiveData<Result<PopularMovies>>()
    val popularMovieData: LiveData<Result<PopularMovies>>
        get() = _popularMovieData

    fun getListPopularMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _popularMovieData.postValue(Result.Loading)
                val movieListResponse = getMovieListUseCase.execute()
                _popularMovieData.postValue(movieListResponse)
            } catch (e: Exception) {
                _popularMovieData.postValue(Result.Error(e))
            }
        }
    }

}