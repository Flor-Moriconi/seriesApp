package com.florm.mymovies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.florm.mymovies.database.entities.SubscriptionEntity
import com.florm.mymovies.models.TvSerie
import com.florm.mymovies.repositories.SubscriptionRepository

class TvSerieDetailViewModel : ViewModel() {
    var serieDetails = TvSerie()
    var subscriptionRepository = SubscriptionRepository()
    var subscriptionsList : LiveData<List<SubscriptionEntity>>? = subscriptionRepository.getSubscriptions()

    fun makeSubscription() {
        val subscription = getSubscriptionObject()
        subscriptionRepository.addSubscription(subscription)
    }

    fun deleteSubscription() {
        serieDetails.id?.let {
            subscriptionRepository.deleteSubscription(it)
        }
    }

    fun isSubscribed(): Boolean {
        val subscription = subscriptionsList?.value?.find {
            it.serieId == serieDetails.id
        }
        return subscription != null
    }

    private fun getSubscriptionObject(): SubscriptionEntity {
        var idSerie = ""
        var name = ""
        var overview = ""
        var genreIds = ""
        var firstAirDate = ""
        var posterPath = ""

        serieDetails.id?.let { idSerie = it }
        serieDetails.name?.let { name = it }
        serieDetails.genreIds?.let { genreIds = it.first() }
        serieDetails.overview?.let { overview = it }
        serieDetails.firstAirDate?.let { firstAirDate = it }
        serieDetails.posterPath?.let { posterPath = it }

        return SubscriptionEntity(idSerie, name, genreIds, overview, firstAirDate, posterPath)
    }
}