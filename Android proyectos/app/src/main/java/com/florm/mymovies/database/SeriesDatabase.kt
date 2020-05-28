package com.florm.mymovies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.florm.mymovies.database.daos.SubscriptionDAO
import com.florm.mymovies.database.entities.SubscriptionEntity

@Database(entities = [SubscriptionEntity:: class], version = 1)
abstract class SeriesDatabase(): RoomDatabase() {
    abstract fun subscriptionDao(): SubscriptionDAO
}
