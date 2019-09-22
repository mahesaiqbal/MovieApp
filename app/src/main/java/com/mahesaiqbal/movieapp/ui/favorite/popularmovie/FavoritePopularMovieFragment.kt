package com.mahesaiqbal.movieapp.ui.favorite.popularmovie


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mahesaiqbal.movieapp.R

/**
 * A simple [Fragment] subclass.
 */
class FavoritePopularMovieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_popular_movie, container, false)
    }


}
