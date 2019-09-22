package com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movie")
data class PopularMovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String,
    @ColumnInfo(name = "original_language")
    var originalLanguage: String,
    @ColumnInfo(name = "overview")
    var overview: String,
    @ColumnInfo(name = "popularity")
    var popularity: Double,
    @ColumnInfo(name = "poster_path")
    var posterPath: String,
    @ColumnInfo(name = "release_date")
    var releaseDate: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    var voteCount: Int,
    @ColumnInfo(name = "favorited")
    var favorited: Boolean = false
)