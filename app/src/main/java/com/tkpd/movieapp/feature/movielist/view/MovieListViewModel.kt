package com.tkpd.movieapp.feature.movielist.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tkpd.movieapp.feature.movielist.domain.GetMovieListUseCase
import com.tkpd.movieapp.model.MovieItem
import com.tkpd.movieapp.model.PopularMovies
import com.tkpd.movieapp.util.Result
import kotlinx.coroutines.*
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
                val movieData1Response = getMovieListUseCase.execute()
                val movieData2Response = getAnotherMovieData()
                mapMovieData2ToMovieData1(movieData1Response, movieData2Response)
                _popularMovieData.postValue(movieData1Response)
            } catch (e: Exception) {
                _popularMovieData.postValue(Result.Error(e))
            }
        }
    }

    private fun mapMovieData2ToMovieData1(movieData1Response: Result<PopularMovies>?,
                                          movieData2Response: MovieItem) {
        (movieData1Response as? Result.Success)?.data?.movieItems?.add(movieData2Response)
    }

    private suspend fun getAnotherMovieData(): MovieItem {
        delay(3000)
        return MovieItem(
            title = "Another Movie Title"
        )
    }

}