package com.mahesaiqbal.movieapp.ui.popularmovie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mahesaiqbal.movieapp.R
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.ui.popularmovie.PopularMoviePagedAdapter.PopularMovieViewHolder
import kotlinx.android.synthetic.main.item_popular_movie.view.*

class PopularMoviePagedAdapter(var callback: PopularMovieCallback, var favoriteCallback: FavoritePopularMovieCallback) :
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

        fun bindItem(popularMovie: PopularMovieEntity, callback: PopularMovieCallback) {
            itemView.tv_title.text = popularMovie.title
            itemView.img_favorited.setOnClickListener { favoriteCallback.onFavoriteClick(popularMovie) }

            if (popularMovie.favorited) {
                itemView.img_favorited.setImageResource(R.drawable.ic_favorite_red)
            } else {
                itemView.img_favorited.setImageResource(R.drawable.ic_favorite_grey)
            }

            itemView.tv_categories.text = "Movie Categories"

            itemView.setOnClickListener { callback.onItemClick(popularMovie) }

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${popularMovie.posterPath}")
                .override(500, 500)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_load).error(R.drawable.ic_sad))
                .into(itemView.img_poster)
        }
    }

    interface PopularMovieCallback {
        fun onItemClick(popularMovie: PopularMovieEntity)
    }

    interface FavoritePopularMovieCallback {
        fun onFavoriteClick(popularMovie: PopularMovieEntity)
    }
}
