package com.mahesaiqbal.movieapp.data.source.remote.response.toprated

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    var id: Int,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int
)