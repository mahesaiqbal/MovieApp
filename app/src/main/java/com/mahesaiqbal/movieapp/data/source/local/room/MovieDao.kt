package com.mahesaiqbal.movieapp.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource.Factory
import androidx.room.*
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM popular_movie")
    fun getPopularMovie(): Factory<Int, PopularMovieEntity>

    @Query("SELECT * FROM popular_movie WHERE favorited = 1")
    fun getFavoritedPopularMovie(): Factory<Int, PopularMovieEntity>

    @Query("SELECT * FROM top_rated_movie")
    fun getTopRatedMovie(): Factory<Int, TopRatedMovieEntity>

    @Query("SELECT * FROM top_rated_movie WHERE favorited = 1")
    fun getFavoritedTopRatedMovie(): Factory<Int, TopRatedMovieEntity>

    @Query("SELECT * FROM popular_movie WHERE id = :movieId")
    fun getDetailPopularMovie(movieId: Int): LiveData<PopularMovieEntity>

    @Query("SELECT * FROM top_rated_movie WHERE id = :movieId")
    fun getDetailTopRatedMovie(movieId: Int): LiveData<TopRatedMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopularMovie(popularMovie: MutableList<PopularMovieEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopRatedMovie(topRatedMovie: MutableList<TopRatedMovieEntity>): LongArray

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updatePopularMovie(popularMovie: PopularMovieEntity)

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updateTopRatedMovie(topRatedMovie: TopRatedMovieEntity)
}