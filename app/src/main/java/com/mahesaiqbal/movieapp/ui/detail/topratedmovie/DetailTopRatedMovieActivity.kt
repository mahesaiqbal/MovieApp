package com.mahesaiqbal.movieapp.ui.detail.topratedmovie

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mahesaiqbal.movieapp.R
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
import com.mahesaiqbal.movieapp.data.source.remote.response.trailer.ResultTrailerMovie
import com.mahesaiqbal.movieapp.ui.detail.TrailerMovieAdapter
import com.mahesaiqbal.movieapp.ui.detail.TrailerMovieAdapter.TrailerMovieCallback
import com.mahesaiqbal.movieapp.utils.dateFormat
import com.mahesaiqbal.movieapp.viewmodel.ViewModelFactory
import com.mahesaiqbal.movieapp.vo.Resource
import com.mahesaiqbal.movieapp.vo.Status
import kotlinx.android.synthetic.main.activity_detail_top_rated_movie.*

class DetailTopRatedMovieActivity : AppCompatActivity(), TrailerMovieCallback {

    lateinit var trailerMovieAdapter: TrailerMovieAdapter
    lateinit var viewModel: DetailTopRatedMovieViewModel

    var trailers: MutableList<ResultTrailerMovie> = mutableListOf()

    companion object {
        fun obtainViewModel(activity: AppCompatActivity): DetailTopRatedMovieViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(DetailTopRatedMovieViewModel::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_top_rated_movie)

        viewModel = obtainViewModel(this)

        val extras: Bundle? = intent.extras
        if (extras != null) {
            val movieId = extras.getInt("movie_id")
            if (movieId != null) {
                viewModel.setMovieIdValue(movieId)
            }
        }

        progress_bar.visibility = View.VISIBLE

        trailerMovieAdapter = TrailerMovieAdapter(trailers, this)

        viewModel.topRatedMovieDetail.observe(this, getDetailMovie)

        viewModel.topRatedMovieDetail.observe(this, movieFavorited)

        viewModel.getAllTrailerMovies().observe(this, getTrailers)

        favoriteMovieCLicked()
    }

    private fun favoriteMovieCLicked() {
        img_favorited.setOnClickListener {
            viewModel.setFavorite()
        }
    }

    private val getDetailMovie = Observer<Resource<TopRatedMovieEntity>> { movieWithDetail ->
        if (movieWithDetail != null) {
            when (movieWithDetail.status) {
                Status.LOADING -> progress_bar.visibility = View.VISIBLE
                Status.SUCCESS -> if (movieWithDetail.data != null) {
                    progress_bar.visibility = View.GONE

                    populateMovie(movieWithDetail.data)
                }
                Status.ERROR -> progress_bar.visibility = View.GONE
            }
        }
    }

    private val movieFavorited = Observer<Resource<TopRatedMovieEntity>> { movieFavorited ->
        if (movieFavorited != null) {
            when (movieFavorited.status) {
                Status.LOADING -> progress_bar.visibility = View.VISIBLE
                Status.SUCCESS -> if ( movieFavorited.data != null) {
                    progress_bar.visibility = View.GONE

                    val state = movieFavorited.data.favorited
                    setFavoriteState(state)
                }
                Status.ERROR -> progress_bar.visibility = View.GONE
            }
        }
    }

    private val getTrailers = Observer<MutableList<ResultTrailerMovie>> { resultTrailer ->
        if (resultTrailer != null) {
            progress_bar.visibility = View.GONE
            trailerMovieAdapter = TrailerMovieAdapter(resultTrailer, this)

            rv_trailers.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = trailerMovieAdapter
            }

            trailerMovieAdapter.notifyDataSetChanged()
        }
    }

    private fun populateMovie(movie: TopRatedMovieEntity) {
        tv_title.text = movie.title
        tv_release_date.text = dateFormat(movie.releaseDate)
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

    override fun onTrailerClick(trailerMovie: ResultTrailerMovie) {
        val youtubeUrl = "https://www.youtube.com/watch?v=${trailerMovie.key}"
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
        startActivity(webIntent)
    }
}
