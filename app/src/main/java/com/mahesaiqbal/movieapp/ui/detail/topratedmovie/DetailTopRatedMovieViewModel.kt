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

//    fun getDetailPopularMovieTest(): LiveData<Resource<PopularMovieEntity>> = movieRepository.getDetailPopularMovie(429203)

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

//    fun getTrailerMovie(): LiveData<Resource<PagedList<TrailerMovieEntity>>> = movieRepository.getTrailerMovie(movieId.value!!)

//    var trailerMovie: LiveData<Resource<PagedList<TrailerMovieEntity>>> = Transformations.switchMap(movieId) {
//            movieId -> movieRepository.getTrailerMovie(movieId)
//    }

    var movie = MutableLiveData<MutableList<ResultTrailerMovie>>()

    fun getAllTrailerMovies(): MutableLiveData<MutableList<ResultTrailerMovie>> = movieRepository.getTrailerMovie(movieId.value!!)

    fun getTrailerMovies() {
        movie = movieRepository.getTrailerMovie(movieId.value!!)
    }

    override fun onCleared() {
        super.onCleared()
        remoteRepository.onDestroy()
    }
}
