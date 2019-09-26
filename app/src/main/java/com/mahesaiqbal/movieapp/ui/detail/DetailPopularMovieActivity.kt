package com.mahesaiqbal.movieapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mahesaiqbal.movieapp.R
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.viewmodel.ViewModelFactory
import com.mahesaiqbal.movieapp.vo.Resource
import com.mahesaiqbal.movieapp.vo.Status.*
import kotlinx.android.synthetic.main.activity_detail_popular_movie.*

class DetailPopularMovieActivity : AppCompatActivity() {

    lateinit var viewModel: DetailPopularMovieViewModel

    companion object {
        fun obtainViewModel(activity: AppCompatActivity): DetailPopularMovieViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(DetailPopularMovieViewModel::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_popular_movie)

        viewModel = obtainViewModel(this)

        val extras: Bundle? = intent.extras
        if (extras != null) {
            val movieId = extras.getInt("movie_id")
            if (movieId != null) {
                viewModel.setMovieIdValue(movieId)
            }
        }

        viewModel.popularMovieDetail.observe(this, getDetailMovie)

        viewModel.popularMovieDetail.observe(this, movieFavorited)

        favoriteMovieCLicked()
    }

    private fun favoriteMovieCLicked() {
        img_favorited.setOnClickListener {
            viewModel.setFavorite()
        }
    }

    private val getDetailMovie = Observer<Resource<PopularMovieEntity>> { movieWithDetail ->
        if (movieWithDetail != null) {
            when (movieWithDetail.status) {
                LOADING -> progress_bar.visibility = View.VISIBLE
                SUCCESS -> if (movieWithDetail.data != null) {
                    progress_bar.visibility = View.GONE

                    populateMovie(movieWithDetail.data)
                }
                ERROR -> progress_bar.visibility = View.GONE
            }
        }
    }

    private val movieFavorited = Observer<Resource<PopularMovieEntity>> { movieFavorited ->
        if (movieFavorited != null) {
            when (movieFavorited.status) {
                LOADING -> progress_bar.visibility = View.VISIBLE
                SUCCESS -> if ( movieFavorited.data != null) {
                    progress_bar.visibility = View.GONE

                    val state = movieFavorited.data.favorited
                    setFavoriteState(state)
                }
                ERROR -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun populateMovie(movie: PopularMovieEntity) {
        tv_title.text = movie.title
        tv_release_date.text = movie.releaseDate
        tv_overview.text = movie.overview
        tv_vote_average.text = "${movie.voteAverage}/10"
        tv_vote_count.text = "${movie.voteCount} votes"
        tv_original_language.text = movie.originalLanguage

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .apply(RequestOptions.placeholderOf(R.drawable.ic_load).error(R.drawable.ic_sad))
            .into(img_poster_path)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original${movie.backdropPath}")
            .apply(RequestOptions.placeholderOf(R.drawable.ic_load).error(R.drawable.ic_sad))
            .into(img_backdrop_path)
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            img_favorited.setImageResource(R.drawable.ic_favorite_red)
        } else {
            img_favorited.setImageResource(R.drawable.ic_favorite_grey)
        }
    }
}
