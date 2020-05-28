package com.florm.mymovies.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.florm.mymovies.database.entities.SubscriptionEntity

@Dao
interface SubscriptionDAO {
    @Insert
    fun insert(subscription: SubscriptionEntity)

    @Query("SELECT * FROM " + SubscriptionEntity.TABLE_NAME)
    fun getAllSubscriptions(): LiveData<List<SubscriptionEntity>>

    @Query("DELETE FROM " + SubscriptionEntity.TABLE_NAME + " WHERE serie_id = :serieId")
    fun delete(vararg serieId: String)
}