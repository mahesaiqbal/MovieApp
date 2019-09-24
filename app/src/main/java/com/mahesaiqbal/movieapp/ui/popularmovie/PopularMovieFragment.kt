package com.mahesaiqbal.movieapp.ui.popularmovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager

import com.mahesaiqbal.movieapp.R
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.viewmodel.ViewModelFactory
import com.mahesaiqbal.movieapp.ui.popularmovie.PopularMoviePagedAdapter.PopularMovieCallback
import com.mahesaiqbal.movieapp.ui.popularmovie.PopularMoviePagedAdapter.FavoritePopularMovieCallback
import com.mahesaiqbal.movieapp.vo.Resource
import com.mahesaiqbal.movieapp.vo.Status.*
import kotlinx.android.synthetic.main.fragment_popular_movie.*

class PopularMovieFragment : Fragment(), PopularMovieCallback, FavoritePopularMovieCallback {

    lateinit var popularMovieAdapter: PopularMoviePagedAdapter
    lateinit var viewModel: PopularMovieViewModel

    companion object {
        fun obtainViewModel(activity: FragmentActivity): PopularMovieViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(PopularMovieViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            progress_bar.visibility = View.VISIBLE

            viewModel = obtainViewModel(activity!!)

            popularMovieAdapter = PopularMoviePagedAdapter(this, this)

            viewModel.getAllPopularMovies().observe(this, getPopularMovie)

            rv_popular_movie.apply {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = popularMovieAdapter
            }
        }
    }

    private val getPopularMovie = Observer<Resource<PagedList<PopularMovieEntity>>> { resultMovie ->
        if (resultMovie != null) {
            when (resultMovie.status) {
                LOADING -> progress_bar.visibility = View.VISIBLE
                SUCCESS -> {
                    progress_bar.visibility = View.GONE

                    popularMovieAdapter.submitList(resultMovie.data!!)
                    popularMovieAdapter.notifyDataSetChanged()
                }
                ERROR -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemClick(popularMovie: PopularMovieEntity) {
        Toast.makeText(activity, popularMovie.title, Toast.LENGTH_SHORT).show()
    }

    override fun onFavoriteClick(popularMovie: PopularMovieEntity) {
        val newState = !popularMovie.favorited

        viewModel.setFavorite(popularMovie, newState)

        popularMovieAdapter.notifyDataSetChanged()
    }
}
