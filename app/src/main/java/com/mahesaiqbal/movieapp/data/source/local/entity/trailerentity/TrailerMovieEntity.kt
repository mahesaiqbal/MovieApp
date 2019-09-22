package com.mahesaiqbal.movieapp.data.source.local.entity.trailerentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trailer_movie")
data class TrailerMovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "key")
    var key: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "site")
    var site: String
)