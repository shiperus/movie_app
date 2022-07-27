package com.tkpd.movieapp.datasource

import com.tkpd.movieapp.constant.MovieConstant
import com.tkpd.movieapp.model.MovieDetail
import com.tkpd.movieapp.model.PopularMovies
import com.tkpd.movieapp.util.RetrofitInstanceBuilder.CACHE_CONTROL_HEADER
import com.tkpd.movieapp.util.RetrofitInstanceBuilder.CACHE_CONTROL_NO_CACHE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Yehezkiel on 10/05/20
 */
interface MovieAPI {
    @GET("movie/popular")
    suspend fun getTopRatedMovies(@Query(MovieConstant.KEY_PAGE) page: Int,
                                  @Query(MovieConstant.KEY_LANGUAGE) language: String): Response<PopularMovies>

    @GET("movie/popular")
    @Headers("$CACHE_CONTROL_HEADER: $CACHE_CONTROL_NO_CACHE")
    suspend fun getTopRatedMoviesRefresh(@Query(MovieConstant.KEY_PAGE) page: Int,
                                  @Query(MovieConstant.KEY_LANGUAGE) language: String): Response<PopularMovies>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path(MovieConstant.KEY_MOVIE_ID) movieId: Int): Response<MovieDetail>
}
