package com.mahesaiqbal.movieapp.ui.detail

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mahesaiqbal.movieapp.R
import com.mahesaiqbal.movieapp.data.source.remote.response.trailer.ResultTrailerMovie
import com.mahesaiqbal.movieapp.ui.detail.TrailerMovieAdapter.TrailerMovieViewHolder
import kotlinx.android.synthetic.main.item_trailer_movie.view.*

class TrailerMovieAdapter(var trailerMovie: MutableList<ResultTrailerMovie>, var callback: TrailerMovieCallback) : Adapter<TrailerMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerMovieViewHolder
            = TrailerMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trailer_movie, parent, false))

    override fun onBindViewHolder(holder: TrailerMovieViewHolder, position: Int) {
        holder.bindItem(trailerMovie[position], callback)
    }

    override fun getItemCount(): Int = trailerMovie.size

    inner class TrailerMovieViewHolder(itemView: View) : ViewHolder(itemView) {

        fun bindItem(trailerMovie: ResultTrailerMovie, callback: TrailerMovieCallback) {
            itemView.tv_trailer_title.text = "${trailerMovie.name} (${trailerMovie.size}p)"

            itemView.setOnClickListener { callback.onTrailerClick(trailerMovie) }
        }
    }

    interface TrailerMovieCallback {
        fun onTrailerClick(trailerMovie: ResultTrailerMovie)
    }
}