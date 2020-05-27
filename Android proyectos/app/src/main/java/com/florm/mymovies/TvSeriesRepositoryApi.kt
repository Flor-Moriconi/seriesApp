package com.florm.mymovies

import com.florm.mymovies.models.TvSerieGenresResponse
import com.florm.mymovies.models.TvSeriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TvSeriesRepositoryApi {

    @GET("discover/tv?api_key=$API_KEY$BASE_PAGINATION")
    fun getTvSeries(@Query("page") page: Int): Call<TvSeriesResponse>

    @GET("genre/tv/list?api_key=$API_KEY")
    fun getTvSerieGenres(): Call<TvSerieGenresResponse>
}