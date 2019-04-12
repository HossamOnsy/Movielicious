package com.sam.movielicious.network

import com.sam.movielicious.model.MovieDetailModel
import com.sam.movielicious.model.MoviesListModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

    /**
     * Get the list of the pots from the API
     */

    @GET("/3/movie/popular")
    fun getTopRatedMovies(@Query("api_key") api_key: String,@Query("page") page: Int): Observable<MoviesListModel>

    @GET("/3/movie/{id}")
    fun getMovieDetails(  @Path("id") id: String ,  @Query("api_key") api_key: String
                          ): Observable<MovieDetailModel>


}