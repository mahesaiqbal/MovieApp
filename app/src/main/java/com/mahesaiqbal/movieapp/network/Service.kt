package com.mahesaiqbal.movieapp.network

import com.mahesaiqbal.movieapp.data.source.remote.response.detail.DetailResponse
import com.mahesaiqbal.movieapp.data.source.remote.response.popular.PopularMovieResponse
import com.mahesaiqbal.movieapp.data.source.remote.response.toprated.TopRatedMovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("movie/popular")
    fun getPopularMovie(@Query("api_key") apiKey: String): Observable<PopularMovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(@Query("api_key") apiKey: String): Observable<TopRatedMovieResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Observable<DetailResponse>
}