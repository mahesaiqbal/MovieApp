package com.mahesaiqbal.movieapp.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mahesaiqbal.movieapp.ui.favorite.popularmovie.FavoritePopularMovieFragment
import com.mahesaiqbal.movieapp.ui.favorite.toprated.FavoriteTopRatedMovieFragment
import com.mahesaiqbal.movieapp.ui.popularmovie.PopularMovieFragment
import com.mahesaiqbal.movieapp.ui.toprated.TopRatedMovieFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> PopularMovieFragment()
            1 -> TopRatedMovieFragment()
            2 -> FavoritePopularMovieFragment()
            3 -> FavoriteTopRatedMovieFragment()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Popular"
            1 -> "Top Rated"
            2 -> "Favorite Popular"
            3 -> "Favorite Top Rated"
            else -> ""
        }
    }

    override fun getCount(): Int = 4
}
