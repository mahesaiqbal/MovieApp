package com.mahesaiqbal.movieapp.ui.toprated

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
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
import com.mahesaiqbal.movieapp.ui.toprated.TopRatedMoviePagedAdapter.TopRatedMovieViewHolder
import kotlinx.android.synthetic.main.item_top_rated_movie.view.*

class TopRatedMoviePagedAdapter(var callback: TopRatedMovieCallback, var favoriteCallback: FavoriteTopRatedMovieCallback) :
    PagedListAdapter<TopRatedMovieEntity, TopRatedMovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TopRatedMovieEntity>() {
            override fun areItemsTheSame(oldItem: TopRatedMovieEntity, newItem: TopRatedMovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: TopRatedMovieEntity, newItem: TopRatedMovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMovieViewHolder =
        TopRatedMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_top_rated_movie, parent, false))

    override fun onBindViewHolder(holder: TopRatedMovieViewHolder, position: Int) {
        val topRatedMovie: TopRatedMovieEntity = getItem(position)!!
        holder.bindItem(topRatedMovie, callback)
    }

    inner class TopRatedMovieViewHolder(itemView: View) : ViewHolder(itemView) {

        fun bindItem(topRatedMovie: TopRatedMovieEntity, callback: TopRatedMovieCallback) {
            itemView.tv_title.text = topRatedMovie.title
            itemView.img_favorited.setOnClickListener { favoriteCallback.onFavoriteClick(topRatedMovie) }

            if (topRatedMovie.favorited) {
                itemView.img_favorited.setImageResource(R.drawable.ic_favorite_red)
            } else {
                itemView.img_favorited.setImageResource(R.drawable.ic_favorite_grey)
            }

            itemView.setOnClickListener { callback.onItemClick(topRatedMovie) }

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${topRatedMovie.posterPath}")
                .override(500, 500)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_load).error(R.drawable.ic_sad))
                .into(itemView.img_poster)
        }
    }

    interface TopRatedMovieCallback {
        fun onItemClick(topRatedMovie: TopRatedMovieEntity)
    }

    interface FavoriteTopRatedMovieCallback {
        fun onFavoriteClick(topRatedMovie: TopRatedMovieEntity)
    }
}
