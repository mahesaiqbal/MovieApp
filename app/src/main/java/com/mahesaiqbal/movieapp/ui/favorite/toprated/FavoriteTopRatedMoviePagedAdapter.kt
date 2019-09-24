package com.mahesaiqbal.movieapp.ui.favorite.toprated

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
import com.mahesaiqbal.movieapp.ui.favorite.toprated.FavoriteTopRatedMoviePagedAdapter.FavoriteTopRatedMovieViewHolder
import kotlinx.android.synthetic.main.item_top_rated_movie.view.*

class FavoriteTopRatedMoviePagedAdapter(var callback: FavoriteTopRatedMovieCallback) :
    PagedListAdapter<TopRatedMovieEntity, FavoriteTopRatedMovieViewHolder>(DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTopRatedMovieViewHolder =
        FavoriteTopRatedMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_top_rated_movie, parent, false))

    override fun onBindViewHolder(holder: FavoriteTopRatedMovieViewHolder, position: Int) {
        val topRatedMovie: TopRatedMovieEntity = getItem(position)!!
        holder.bindItem(topRatedMovie, callback)
    }

    fun getItemById(swipedPosition: Int): TopRatedMovieEntity = getItem(swipedPosition)!!

    inner class FavoriteTopRatedMovieViewHolder(itemView: View) : ViewHolder(itemView) {

        fun bindItem(topRatedMovie: TopRatedMovieEntity, callback: FavoriteTopRatedMovieCallback) {
            itemView.tv_title.text = topRatedMovie.title

            if (topRatedMovie.favorited) {
                itemView.img_favorited.setImageResource(R.drawable.ic_favorite_red)
            } else {
                itemView.img_favorited.setImageResource(R.drawable.ic_favorite_grey)
            }

            itemView.tv_categories.text = "Movie Categories"

            itemView.setOnClickListener { callback.onItemClick(topRatedMovie) }

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${topRatedMovie.posterPath}")
                .override(500, 500)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_load).error(R.drawable.ic_sad))
                .into(itemView.img_poster)
        }
    }

    interface FavoriteTopRatedMovieCallback {
        fun onItemClick(topRatedMovie: TopRatedMovieEntity)
    }
}