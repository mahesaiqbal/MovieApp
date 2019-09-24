package com.mahesaiqbal.movieapp.ui.favorite.popularmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.vo.Resource

class FavoritePopularMovieViewModel(var movieRepository: MovieRepository) : ViewModel() {

    fun getAllFavoritePopularMovies(): LiveData<Resource<PagedList<PopularMovieEntity>>> = movieRepository.getAllFavoritedPopularMovies()

    fun setFavorite(popularMovie: PopularMovieEntity) {
        val newState = !popularMovie.favorited
        movieRepository.setPopularMovieFavorited(popularMovie, newState)
    }
}