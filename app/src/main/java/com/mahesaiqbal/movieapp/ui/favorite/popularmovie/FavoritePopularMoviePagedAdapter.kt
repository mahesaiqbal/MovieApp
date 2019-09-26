package com.mahesaiqbal.movieapp.ui.favorite.popularmovie

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
import com.mahesaiqbal.movieapp.ui.favorite.popularmovie.FavoritePopularMoviePagedAdapter.FavoritePopularMovieViewHolder
import kotlinx.android.synthetic.main.item_popular_movie.view.*

class FavoritePopularMoviePagedAdapter(var callback: FavoritePopularMovieCallback) :
    PagedListAdapter<PopularMovieEntity, FavoritePopularMovieViewHolder>(DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePopularMovieViewHolder =
        FavoritePopularMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movie, parent, false))

    override fun onBindViewHolder(holder: FavoritePopularMovieViewHolder, position: Int) {
        val popularMovie: PopularMovieEntity = getItem(position)!!
        holder.bindItem(popularMovie, callback)
    }

    fun getItemById(swipedPosition: Int): PopularMovieEntity = getItem(swipedPosition)!!

    inner class FavoritePopularMovieViewHolder(itemView: View) : ViewHolder(itemView) {

        fun bindItem(popularMovie: PopularMovieEntity, callback: FavoritePopularMovieCallback) {
            itemView.tv_title.text = popularMovie.title

            if (popularMovie.favorited) {
                itemView.img_favorited.setImageResource(R.drawable.ic_favorite_red)
            } else {
                itemView.img_favorited.setImageResource(R.drawable.ic_favorite_grey)
            }

            itemView.setOnClickListener { callback.onItemClick(popularMovie) }

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${popularMovie.posterPath}")
                .override(500, 500)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_load).error(R.drawable.ic_sad))
                .into(itemView.img_poster)
        }
    }

    interface FavoritePopularMovieCallback {
        fun onItemClick(popularMovie: PopularMovieEntity)
    }
}