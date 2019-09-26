package com.mahesaiqbal.movieapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
import com.mahesaiqbal.movieapp.data.source.remote.response.trailer.ResultTrailerMovie
import com.mahesaiqbal.movieapp.vo.Resource

interface MovieDataSource {
    fun getAllPopularMovies(): LiveData<Resource<PagedList<PopularMovieEntity>>>

    fun getAllFavoritedPopularMovies(): LiveData<Resource<PagedList<PopularMovieEntity>>>

    fun getAllTopRatedMovies(): LiveData<Resource<PagedList<TopRatedMovieEntity>>>

    fun getAllFavoritedTopRatedMovies(): LiveData<Resource<PagedList<TopRatedMovieEntity>>>

    fun getDetailPopularMovie(movieId: Int): LiveData<Resource<PopularMovieEntity>>

    fun getDetailTopRatedMovie(movieId: Int): LiveData<Resource<TopRatedMovieEntity>>

    fun getTrailerMovie(movieId: Int): MutableLiveData<MutableList<ResultTrailerMovie>>

    fun setPopularMovieFavorited(popularMovie: PopularMovieEntity, state: Boolean)

    fun setTopRatedMovieFavorited(topRatedMovie: TopRatedMovieEntity, state: Boolean)
}
