package com.florm.mymovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.florm.mymovies.views.TVSeriesListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToTvSeriesActivity()
    }

    private fun goToTvSeriesActivity() {
        val intent = Intent(this, TVSeriesListActivity::class.java)
        startActivity(intent)
    }
}
