package com.mahesaiqbal.movieapp.ui.popularmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.data.source.remote.RemoteRepository
import com.mahesaiqbal.movieapp.vo.Resource

class PopularMovieViewModel(var movieRepository: MovieRepository) : ViewModel() {

    private val remoteRepository = RemoteRepository()

    fun getAllPopularMovies(): LiveData<Resource<PagedList<PopularMovieEntity>>> = movieRepository.getAllPopularMovies()

    fun setFavorite(popularMovie: PopularMovieEntity, newState: Boolean) {
        movieRepository.setPopularMovieFavorited(popularMovie, newState)
    }

    override fun onCleared() {
        super.onCleared()
        remoteRepository.onDestroy()
    }
}
