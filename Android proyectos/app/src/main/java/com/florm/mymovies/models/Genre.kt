package com.florm.mymovies.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Genre(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null) : Serializable