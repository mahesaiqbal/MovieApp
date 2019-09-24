package com.mahesaiqbal.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.di.Injection
import com.mahesaiqbal.movieapp.ui.popularmovie.PopularMovieViewModel

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
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}