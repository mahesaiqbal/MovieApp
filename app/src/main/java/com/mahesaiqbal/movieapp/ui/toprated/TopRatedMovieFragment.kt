package com.mahesaiqbal.movieapp.ui.toprated

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
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
import com.mahesaiqbal.movieapp.viewmodel.ViewModelFactory
import com.mahesaiqbal.movieapp.ui.toprated.TopRatedMoviePagedAdapter.TopRatedMovieCallback
import com.mahesaiqbal.movieapp.ui.toprated.TopRatedMoviePagedAdapter.FavoriteTopRatedMovieCallback
import com.mahesaiqbal.movieapp.vo.Resource
import com.mahesaiqbal.movieapp.vo.Status.*
import kotlinx.android.synthetic.main.fragment_top_rated_movie.*

class TopRatedMovieFragment : Fragment(), TopRatedMovieCallback, FavoriteTopRatedMovieCallback {

    lateinit var topRatedMovieAdapter: TopRatedMoviePagedAdapter
    lateinit var viewModel: TopRatedMovieViewModel

    companion object {
        fun obtainViewModel(activity: FragmentActivity): TopRatedMovieViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(TopRatedMovieViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_rated_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            progress_bar.visibility = View.VISIBLE

            viewModel = obtainViewModel(activity!!)

            topRatedMovieAdapter = TopRatedMoviePagedAdapter(this, this)

            viewModel.getAllTopRatedMovies().observe(this, getTopRatedMovie)

            rv_top_rated_movie.apply {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = topRatedMovieAdapter
            }
        }
    }

    private val getTopRatedMovie = Observer<Resource<PagedList<TopRatedMovieEntity>>> { resultMovie ->
        if (resultMovie != null) {
            when (resultMovie.status) {
                LOADING -> progress_bar.visibility = View.VISIBLE
                SUCCESS -> {
                    progress_bar.visibility = View.GONE

                    topRatedMovieAdapter.submitList(resultMovie.data!!)
                    topRatedMovieAdapter.notifyDataSetChanged()
                }
                ERROR -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemClick(topRatedMovie: TopRatedMovieEntity) {
        Toast.makeText(activity, topRatedMovie.title, Toast.LENGTH_SHORT).show()
    }

    override fun onFavoriteClick(topRatedMovie: TopRatedMovieEntity) {
        val newState = !topRatedMovie.favorited

        viewModel.setFavorite(topRatedMovie, newState)

        topRatedMovieAdapter.notifyDataSetChanged()
    }
}
