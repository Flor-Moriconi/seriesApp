package com.florm.mymovies.repositories

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.MutableLiveData
import com.florm.mymovies.RetrofitService
import com.florm.mymovies.models.Genre
import com.florm.mymovies.models.TvSerie
import com.florm.mymovies.models.TvSerieGenresResponse
import com.florm.mymovies.models.TvSeriesResponse

class TvSeriesRepository {

    fun getInstance(): TvSeriesRepository {
        return TvSeriesRepository()
    }

    fun getTvSeries(list: MutableLiveData<List<TvSerie>>, page: Int) {
        val service = RetrofitService().service

        service.getTvSeries(page).enqueue(object : Callback<TvSeriesResponse> {

            override fun onResponse(call: Call<TvSeriesResponse>, response: Response<TvSeriesResponse>) {
                val tvSeriesResponse = response.body()
                tvSeriesResponse?.let { seriesResponse ->
                    list.postValue(seriesResponse.results)
                }
            }

            override fun onFailure(call: Call<TvSeriesResponse>, t: Throwable) {
                t.printStackTrace()
                list.postValue(null)
            }
        })

    }

    fun getTvSeriesGenres(genresList: MutableLiveData<List<Genre>>) {
        val service = RetrofitService().service

        service.getTvSerieGenres().enqueue(object : Callback<TvSerieGenresResponse> {
            override fun onResponse(call: Call<TvSerieGenresResponse>, response: Response<TvSerieGenresResponse>) {
                val genresResponse = response.body()
                genresResponse?.genres?.let { list ->
                    genresList.postValue(list)
                }
            }

            override fun onFailure(call: Call<TvSerieGenresResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}