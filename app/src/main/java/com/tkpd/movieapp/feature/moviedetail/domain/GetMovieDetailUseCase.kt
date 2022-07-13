package com.tkpd.movieapp.feature.moviedetail.domain

import com.tkpd.movieapp.datasource.repository.MovieDetailRepository
import com.tkpd.movieapp.model.MovieDetail
import com.tkpd.movieapp.util.Result

class GetMovieDetailUseCase(private val repository: MovieDetailRepository) {
    suspend fun execute(movieId: Int): Result<MovieDetail>? {
        return repository.getMovieDetailFromAPI(movieId)
    }
}
