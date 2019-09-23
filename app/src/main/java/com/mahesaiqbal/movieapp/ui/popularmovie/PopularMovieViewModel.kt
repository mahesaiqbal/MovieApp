package com.mahesaiqbal.movieapp.ui.popularmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.data.source.remote.RemoteRepository
import com.mahesaiqbal.movieapp.vo.Resource

class PopularMovieViewModel(var movieRepository: MovieRepository) : ViewModel() {

    private val remoteRepository = RemoteRepository()

    fun getAllMovies(): LiveData<Resource<PagedList<PopularMovieEntity>>> = movieRepository.getAllPopularMovies()

    override fun onCleared() {
        super.onCleared()
        remoteRepository.onDestroy()
    }
}
