package com.mahesaiqbal.movieapp.ui.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
import com.mahesaiqbal.movieapp.data.source.remote.RemoteRepository
import com.mahesaiqbal.movieapp.vo.Resource

class TopRatedMovieViewModel(var movieRepository: MovieRepository) : ViewModel() {

    private val remoteRepository = RemoteRepository()

    fun getAllTopRatedMovies(): LiveData<Resource<PagedList<TopRatedMovieEntity>>> = movieRepository.getAllTopRatedMovies()

    fun setFavorite(topRatedMovie: TopRatedMovieEntity, newState: Boolean) {
        movieRepository.setTopRatedMovieFavorited(topRatedMovie, newState)
    }

    override fun onCleared() {
        super.onCleared()
        remoteRepository.onDestroy()
    }
}
