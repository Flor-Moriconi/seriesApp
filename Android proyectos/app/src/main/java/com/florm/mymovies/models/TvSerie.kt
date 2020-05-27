package com.florm.mymovies.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TvSerie(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("genre_ids")
    var genreIds: List<String>? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("first_air_date")
    var firstAirDate: String? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null) : Serializable