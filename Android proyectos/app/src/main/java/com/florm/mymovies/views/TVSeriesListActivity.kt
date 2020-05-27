package com.florm.mymovies.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.florm.mymovies.R
import com.florm.mymovies.models.Genre
import com.florm.mymovies.models.TvSerie
import com.florm.mymovies.viewmodels.TvSeriesViewModel

class TVSeriesListActivity: AppCompatActivity(), TvSeriesAdapter.TvSerieActivityBridge {
    private var tvSeriesRecyclerView: RecyclerView? = null
    private var tvSeriesAdapter: TvSeriesAdapter? = null
    private var progressBar: ProgressBar? = null
    private lateinit var viewModel : TvSeriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_series)

        bindViewElements()
        createViewModel()
        observeViewModel()
        getTvSeries()
    }

    private fun bindViewElements() {
        tvSeriesRecyclerView = findViewById(R.id.rv_tv_series)
        progressBar = findViewById(R.id.progress_bar)
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(this).get(TvSeriesViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.tvSeriesList.observe(this, Observer<List<TvSerie>> { list ->
            list.let {
                viewModel.getTvSeriesGenres()
            }
        })

        viewModel.genresList.observe(this, Observer<List<Genre>> { genresList ->
            genresList.let { genreList ->
                viewModel.tvSeriesList.value?.let { tvSeriesList ->
                    loadTvSeriesList(tvSeriesList, genresList)
                }
            }
        })
    }

    private fun loadTvSeriesList(list: List<TvSerie>, genreList: List<Genre>) {
        tvSeriesAdapter = TvSeriesAdapter(list, genreList, this)
        tvSeriesRecyclerView?.layoutManager = LinearLayoutManager(this)
        tvSeriesRecyclerView?.adapter = tvSeriesAdapter

        progressBar?.visibility = View.GONE
    }

    private fun goToSerieDetail(serie: TvSerie) {
        val intent = Intent(this, TVSerieDetailActivity::class.java)
        intent.putExtra("EXTRA_SERIE", serie)
        startActivity(intent)
    }

    override fun onSerieClicked(item: TvSerie) {
        goToSerieDetail(item)
    }

    private fun getTvSeries() {
        viewModel.getTvSeries()
    }
}