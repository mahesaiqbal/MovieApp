package com.mahesaiqbal.movieapp.data.source.remote.response.popular

import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    @SerializedName("results")
    var results: MutableList<ResultPopularMovie>
)