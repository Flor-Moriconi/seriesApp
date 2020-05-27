package com.florm.mymovies.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.florm.mymovies.models.Genre
import com.florm.mymovies.models.TvSerie
import com.florm.mymovies.repositories.TvSeriesRepository

class TvSeriesViewModel : ViewModel() {

    var repository: TvSeriesRepository = TvSeriesRepository().getInstance()
    var tvSeriesList = MutableLiveData<List<TvSerie>>()
    var genresList = MutableLiveData<List<Genre>>()

    fun getTvSeries(page: Int) {
        repository.getTvSeries(tvSeriesList, page)
    }


    fun getTvSeriesGenres() {
        repository.getTvSeriesGenres(genresList)
    }
}