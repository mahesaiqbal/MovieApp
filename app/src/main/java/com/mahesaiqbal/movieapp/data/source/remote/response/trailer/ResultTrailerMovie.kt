package com.mahesaiqbal.movieapp.data.source.remote.response.trailer


import com.google.gson.annotations.SerializedName

data class ResultTrailerMovie(
    @SerializedName("id")
    var id: String,
    @SerializedName("key")
    var key: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("site")
    var site: String
)