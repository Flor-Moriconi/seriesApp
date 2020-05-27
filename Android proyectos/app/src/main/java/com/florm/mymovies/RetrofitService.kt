package com.florm.mymovies

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    private var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service = retrofit.create<TvSeriesRepositoryApi>(TvSeriesRepositoryApi::class.java)
}