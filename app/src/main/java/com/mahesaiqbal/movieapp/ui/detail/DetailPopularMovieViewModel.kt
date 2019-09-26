package com.mahesaiqbal.movieapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.data.source.remote.RemoteRepository
import com.mahesaiqbal.movieapp.vo.Resource

class DetailPopularMovieViewModel(var movieRepository: MovieRepository) : ViewModel() {

    private val remoteRepository = RemoteRepository()

    var movieId: MutableLiveData<Int> = MutableLiveData()

    fun setMovieIdValue(id: Int) {
        movieId.value = id
    }

//    fun getDetailPopularMovieTest(): LiveData<Resource<PopularMovieEntity>> = movieRepository.getDetailPopularMovie(429203)

    var popularMovieDetail: LiveData<Resource<PopularMovieEntity>> = Transformations.switchMap(movieId) {
            movieId -> movieRepository.getDetailPopularMovie(movieId)
    }

    fun setFavorite() {
        val popularMovieWithDetail = popularMovieDetail.value
        if (popularMovieWithDetail != null) {
            val popularMovieEntity: PopularMovieEntity? = popularMovieWithDetail.data
            val newState = !popularMovieEntity!!.favorited

            movieRepository.setPopularMovieFavorited(popularMovieEntity, newState)
        }
    }

    override fun onCleared() {
        super.onCleared()
        remoteRepository.onDestroy()
    }
}
