package com.mahesaiqbal.movieapp.data.source.remote.response.toprated


import com.google.gson.annotations.SerializedName

data class TopRatedMovieResponse(
    @SerializedName("results")
    var results: MutableList<ResultTopRatedMovie>
)