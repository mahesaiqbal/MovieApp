package com.mahesaiqbal.movieapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource.Factory
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
import com.mahesaiqbal.movieapp.data.source.local.room.MovieDao

class LocalRepository(val mMovieDao: MovieDao) {

    companion object {
        private var INSTANCE: LocalRepository? = null

        fun getInstance(movieDao: MovieDao): LocalRepository {
            if (INSTANCE == null) {
                INSTANCE = LocalRepository(movieDao)
            }
            return INSTANCE!!
        }
    }

    fun getAllPopularMovie(): Factory<Int, PopularMovieEntity> = mMovieDao.getPopularMovie()

    fun getFavoritedPopularMovie(): Factory<Int, PopularMovieEntity> = mMovieDao.getFavoritedPopularMovie()

    fun getAllTopRatedMovie(): Factory<Int, TopRatedMovieEntity> = mMovieDao.getTopRatedMovie()

    fun getFavoritedTopRatedMovie(): Factory<Int, TopRatedMovieEntity> = mMovieDao.getFavoritedTopRatedMovie()

    fun getDetailPopularMovie(movieId: Int): LiveData<PopularMovieEntity> = mMovieDao.getDetailPopularMovie(movieId)

    fun getDetailTopRatedMovie(movieId: Int): LiveData<TopRatedMovieEntity> = mMovieDao.getDetailTopRatedMovie(movieId)

    fun insertPopularMovie(popularMovie: MutableList<PopularMovieEntity>) {
        mMovieDao.insertPopularMovie(popularMovie)
    }

    fun insertTopRatedMovie(topRatedMovie: MutableList<TopRatedMovieEntity>) {
        mMovieDao.insertTopRatedMovie(topRatedMovie)
    }

    fun setPopularMovieFavorite(popularMovie: PopularMovieEntity, newState: Boolean) {
        popularMovie.favorited = newState
        mMovieDao.updatePopularMovie(popularMovie)
    }

    fun setTopRatedMovieFavorite(topRatedMovie: TopRatedMovieEntity, newState: Boolean) {
        topRatedMovie.favorited = newState
        mMovieDao.updateTopRatedMovie(topRatedMovie)
    }
}