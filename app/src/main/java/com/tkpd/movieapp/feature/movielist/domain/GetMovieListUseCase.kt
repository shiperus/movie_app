package com.tkpd.movieapp.feature.movielist.domain

import com.tkpd.movieapp.datasource.repository.MovieListRepository
import com.tkpd.movieapp.model.PopularMovies
import com.tkpd.movieapp.util.Result

class GetMovieListUseCase(private val movieListRepository: MovieListRepository) {
    suspend fun execute(): Result<PopularMovies>? {
        return movieListRepository.getMovieListFromAPI()
    }
}