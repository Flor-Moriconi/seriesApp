package com.florm.mymovies

import android.app.Application
import androidx.room.Room
import com.florm.mymovies.database.SeriesDatabase

class MyApplication : Application() {
    companion object {
        var database : SeriesDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext,
            SeriesDatabase::class.java, "series_database")
            .allowMainThreadQueries()
            .build()
    }
}