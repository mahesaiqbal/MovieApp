package com.mahesaiqbal.movieapp.ui.detail.topratedmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
import com.mahesaiqbal.movieapp.data.source.remote.RemoteRepository
import com.mahesaiqbal.movieapp.data.source.remote.response.trailer.ResultTrailerMovie
import com.mahesaiqbal.movieapp.vo.Resource

class DetailTopRatedMovieViewModel(var movieRepository: MovieRepository) : ViewModel() {

    private val remoteRepository = RemoteRepository()

    var movieId: MutableLiveData<Int> = MutableLiveData()

    fun setMovieIdValue(id: Int) {
        movieId.value = id
    }

    fun getDetailTopRatedMovieTest(): LiveData<Resource<TopRatedMovieEntity>> = movieRepository.getDetailTopRatedMovie(19404)

    var topRatedMovieDetail: LiveData<Resource<TopRatedMovieEntity>> = Transformations.switchMap(movieId) {
            movieId -> movieRepository.getDetailTopRatedMovie(movieId)
    }

    fun setFavorite() {
        val topRatedMovieWithDetail = topRatedMovieDetail.value
        if (topRatedMovieWithDetail != null) {
            val topRatedMovieEntity: TopRatedMovieEntity? = topRatedMovieWithDetail.data
            val newState = !topRatedMovieEntity!!.favorited

            movieRepository.setTopRatedMovieFavorited(topRatedMovieEntity, newState)
        }
    }

    var trailerMovie = MutableLiveData<MutableList<ResultTrailerMovie>>()

    fun getAllTrailerMovies(): MutableLiveData<MutableList<ResultTrailerMovie>> = movieRepository.getTrailerMovie(movieId.value!!)

    fun getTrailerMovies() {
        trailerMovie = movieRepository.getTrailerMovie(19404)
    }

    override fun onCleared() {
        super.onCleared()
        remoteRepository.onDestroy()
    }
}
