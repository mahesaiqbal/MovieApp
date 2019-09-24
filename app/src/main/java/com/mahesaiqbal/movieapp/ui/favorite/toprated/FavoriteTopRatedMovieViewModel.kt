package com.mahesaiqbal.movieapp.ui.favorite.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
import com.mahesaiqbal.movieapp.vo.Resource

class FavoriteTopRatedMovieViewModel(var movieRepository: MovieRepository) : ViewModel() {

    fun getAllFavoriteTopRatedMovies(): LiveData<Resource<PagedList<TopRatedMovieEntity>>> = movieRepository.getAllFavoritedTopRatedMovies()

    fun setFavorite(topRatedMovie: TopRatedMovieEntity) {
        val newState = !topRatedMovie.favorited
        movieRepository.setTopRatedMovieFavorited(topRatedMovie, newState)
    }
}
