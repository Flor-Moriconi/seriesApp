package com.florm.mymovies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.florm.mymovies.database.entities.SubscriptionEntity
import com.florm.mymovies.models.Genre
import com.florm.mymovies.models.TvSerie
import com.florm.mymovies.repositories.SubscriptionRepository
import com.florm.mymovies.repositories.TvSeriesRepository

class TvSeriesViewModel : ViewModel() {

    var repository: TvSeriesRepository = TvSeriesRepository().getInstance()
    var subscriptionRepository = SubscriptionRepository()
    var tvSeriesList = MutableLiveData<List<TvSerie>>()
    var genresList = MutableLiveData<List<Genre>>()
    var subscriptionList : LiveData<List<SubscriptionEntity>>? = subscriptionRepository.getSubscriptions()

    fun getTvSeries(page: Int) {
        repository.getTvSeries(tvSeriesList, page)
    }

    fun getTvSeriesGenres() {
        repository.getTvSeriesGenres(genresList)
    }

}