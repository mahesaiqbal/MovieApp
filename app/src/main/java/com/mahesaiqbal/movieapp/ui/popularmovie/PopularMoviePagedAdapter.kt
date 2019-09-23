package com.mahesaiqbal.movieapp.ui.popularmovie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mahesaiqbal.movieapp.R
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.ui.popularmovie.PopularMoviePagedAdapter.PopularMovieViewHolder

class PopularMoviePagedAdapter(var callback: MoviesFragmentCallback) :
    PagedListAdapter<PopularMovieEntity, PopularMovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularMovieEntity>() {
            override fun areItemsTheSame(oldItem: PopularMovieEntity, newItem: PopularMovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: PopularMovieEntity, newItem: PopularMovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder =
        PopularMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movie, parent, false))

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val popularMovie: PopularMovieEntity = getItem(position)!!
        holder.bindItem(popularMovie, callback)
    }

    inner class PopularMovieViewHolder(itemView: View) : ViewHolder(itemView) {

        fun bindItem(popularMovie: PopularMovieEntity, callback: MoviesFragmentCallback) {
            itemView.tv_title.text = movie.title
            itemView.tv_overview.text = movie.overview
            itemView.tv_release_date.text = movie.releaseDate

            itemView.setOnClickListener { callback.onItemClick(movie) }

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .override(500, 500)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_movie).error(R.drawable.ic_error))
                .into(itemView.img_poster)
        }
    }

    interface PopularMovieCallback {
        fun onItemClick(popularMovie: PopularMovieEntity)
    }
}
