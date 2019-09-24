package com.mahesaiqbal.movieapp.ui.favorite.toprated

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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

import com.mahesaiqbal.movieapp.R
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
import com.mahesaiqbal.movieapp.viewmodel.ViewModelFactory
import com.mahesaiqbal.movieapp.ui.favorite.toprated.FavoriteTopRatedMoviePagedAdapter.FavoriteTopRatedMovieCallback
import com.mahesaiqbal.movieapp.vo.Resource
import com.mahesaiqbal.movieapp.vo.Status.*
import kotlinx.android.synthetic.main.fragment_favorite_top_rated_movie.*

class FavoriteTopRatedMovieFragment : Fragment(), FavoriteTopRatedMovieCallback {

    lateinit var favTopRatedMovieAdapter: FavoriteTopRatedMoviePagedAdapter
    lateinit var viewModel: FavoriteTopRatedMovieViewModel

    companion object {
        fun obtainViewModel(activity: FragmentActivity): FavoriteTopRatedMovieViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(FavoriteTopRatedMovieViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_top_rated_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            progress_bar.visibility = View.VISIBLE

            viewModel = obtainViewModel(activity!!)

            favTopRatedMovieAdapter = FavoriteTopRatedMoviePagedAdapter(this)

            viewModel.getAllFavoriteTopRatedMovies().observe(this, getTopRatedMovie)

            rv_fav_top_rated_movie.apply {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = favTopRatedMovieAdapter
                itemTouchHelper.attachToRecyclerView(this)
            }
        }
    }

    private val getTopRatedMovie = Observer<Resource<PagedList<TopRatedMovieEntity>>> { favMovie ->
        if (favMovie != null) {
            when (favMovie.status) {
                LOADING -> progress_bar.visibility = View.VISIBLE
                SUCCESS -> {
                    progress_bar.visibility = View.GONE

                    favTopRatedMovieAdapter.submitList(favMovie.data!!)
                    favTopRatedMovieAdapter.notifyDataSetChanged()
                }
                ERROR -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = favTopRatedMovieAdapter.getItemById(swipedPosition)

                viewModel.setFavorite(movieEntity)

                val snackbar = Snackbar.make(view!!, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { v -> viewModel.setFavorite(movieEntity) }
                snackbar.show()
            }
        }
    })

    override fun onItemClick(topRatedMovie: TopRatedMovieEntity) {

    }
}
