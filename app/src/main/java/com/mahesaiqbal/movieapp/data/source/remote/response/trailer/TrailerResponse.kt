package com.mahesaiqbal.movieapp.data.source.remote.response.trailer


import com.google.gson.annotations.SerializedName

data class TrailerResponse(
    @SerializedName("results")
    var results: List<Result>
)