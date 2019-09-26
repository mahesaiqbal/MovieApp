package com.mahesaiqbal.movieapp.ui.favorite.popularmovie

import android.content.Intent
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
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.snackbar.Snackbar
import com.mahesaiqbal.movieapp.R
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.ui.detail.popularmovie.DetailPopularMovieActivity
import com.mahesaiqbal.movieapp.viewmodel.ViewModelFactory
import com.mahesaiqbal.movieapp.ui.favorite.popularmovie.FavoritePopularMoviePagedAdapter.FavoritePopularMovieCallback
import com.mahesaiqbal.movieapp.vo.Resource
import com.mahesaiqbal.movieapp.vo.Status.*
import kotlinx.android.synthetic.main.fragment_favorite_popular_movie.*

class FavoritePopularMovieFragment : Fragment(), FavoritePopularMovieCallback {

    lateinit var favPopularMovieAdapter: FavoritePopularMoviePagedAdapter
    lateinit var viewModel: FavoritePopularMovieViewModel

    companion object {
        fun obtainViewModel(activity: FragmentActivity): FavoritePopularMovieViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(FavoritePopularMovieViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_popular_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            progress_bar.visibility = View.VISIBLE

            viewModel = obtainViewModel(activity!!)

            favPopularMovieAdapter = FavoritePopularMoviePagedAdapter(this)

            viewModel.getAllFavoritePopularMovies().observe(this, getPopularMovie)

            rv_fav_popular_movie.apply {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = favPopularMovieAdapter
                itemTouchHelper.attachToRecyclerView(this)
            }
        }
    }

    private val getPopularMovie = Observer<Resource<PagedList<PopularMovieEntity>>> { favMovie ->
        if (favMovie != null) {
            when (favMovie.status) {
                LOADING -> progress_bar.visibility = View.VISIBLE
                SUCCESS -> {
                    progress_bar.visibility = View.GONE

                    favPopularMovieAdapter.submitList(favMovie.data!!)
                    favPopularMovieAdapter.notifyDataSetChanged()
                }
                ERROR -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = favPopularMovieAdapter.getItemById(swipedPosition)

                viewModel.setFavorite(movieEntity)

                val snackbar = Snackbar.make(view!!, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { v -> viewModel.setFavorite(movieEntity) }
                snackbar.show()
            }
        }
    })

    override fun onItemClick(popularMovie: PopularMovieEntity) {
        val intent = Intent(activity, DetailPopularMovieActivity::class.java)
        intent.putExtra("movie_id", popularMovie.id)
        startActivity(intent)
    }
}
