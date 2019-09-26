package com.mahesaiqbal.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.di.Injection
import com.mahesaiqbal.movieapp.ui.detail.popularmovie.DetailPopularMovieViewModel
import com.mahesaiqbal.movieapp.ui.favorite.popularmovie.FavoritePopularMovieViewModel
import com.mahesaiqbal.movieapp.ui.favorite.toprated.FavoriteTopRatedMovieViewModel
import com.mahesaiqbal.movieapp.ui.popularmovie.PopularMovieViewModel
import com.mahesaiqbal.movieapp.ui.toprated.TopRatedMovieViewModel

class ViewModelFactory(movieRepository: MovieRepository) : NewInstanceFactory() {

    var mMovieRepository: MovieRepository = movieRepository

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = ViewModelFactory(Injection.provideRepository(application))
                    }
                }
            }
            return INSTANCE
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularMovieViewModel::class.java)) {
            return PopularMovieViewModel(mMovieRepository) as T
        } else if (modelClass.isAssignableFrom(FavoritePopularMovieViewModel::class.java)) {
            return FavoritePopularMovieViewModel(mMovieRepository) as T
        } else if (modelClass.isAssignableFrom(TopRatedMovieViewModel::class.java)) {
            return TopRatedMovieViewModel(mMovieRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteTopRatedMovieViewModel::class.java)) {
            return FavoriteTopRatedMovieViewModel(mMovieRepository) as T
        } else if (modelClass.isAssignableFrom(DetailPopularMovieViewModel::class.java)) {
            return DetailPopularMovieViewModel(
                mMovieRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}