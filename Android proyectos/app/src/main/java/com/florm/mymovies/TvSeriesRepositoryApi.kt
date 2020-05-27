package com.florm.mymovies

import com.florm.mymovies.models.TvSerieGenresResponse
import com.florm.mymovies.models.TvSeriesResponse
import retrofit2.Call
import retrofit2.http.GET

interface TvSeriesRepositoryApi {

    @GET("discover/tv?api_key=$API_KEY")
    fun getTvSeries(): Call<TvSeriesResponse>

    @GET("genre/tv/list?api_key=$API_KEY")
    fun getTvSerieGenres(): Call<TvSerieGenresResponse>
}