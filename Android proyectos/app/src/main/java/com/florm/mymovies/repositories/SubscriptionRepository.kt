package com.florm.mymovies.repositories

import androidx.lifecycle.LiveData
import com.florm.mymovies.MyApplication
import com.florm.mymovies.database.daos.SubscriptionDAO
import com.florm.mymovies.database.entities.SubscriptionEntity

class SubscriptionRepository {

    private val subscriptionDao: SubscriptionDAO? = MyApplication.database?.subscriptionDao()

    fun addSubscription(subscription: SubscriptionEntity) {
        subscriptionDao?.insert(subscription)
    }

    fun getSubscriptions(): LiveData<List<SubscriptionEntity>>? {
        return subscriptionDao?.getAllSubscriptions()
    }

    fun deleteSubscription(serieId: String) {
        subscriptionDao?.delete(serieId)
    }

}