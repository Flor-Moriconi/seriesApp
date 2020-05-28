package com.florm.mymovies.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.florm.mymovies.R
import com.florm.mymovies.database.entities.SubscriptionEntity
import com.florm.mymovies.models.Genre
import com.florm.mymovies.models.TvSerie
import com.florm.mymovies.viewmodels.TvSeriesViewModel

class TVSeriesListActivity: AppCompatActivity(), TvSeriesAdapter.TvSerieActivityBridge,
    SubscriptionsAdapter.SubscriptionsBridge {

    private var tvSeriesRecyclerView: RecyclerView? = null
    private var tvSeriesAdapter: TvSeriesAdapter? = null

    private var subscriptionsContainer: LinearLayout? = null
    private var subscriptionsRecyclerView: RecyclerView? = null
    private var subscriptionsAdapter: SubscriptionsAdapter? = null

    private var progressBar: ProgressBar? = null
    private lateinit var viewModel : TvSeriesViewModel

    private var page = 1 //First page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_series)

        bindViewElements()
        createViewModel()
        observeViewModel()
        addListeners()
        getTvSeriesGenres()
    }

    private fun bindViewElements() {
        tvSeriesRecyclerView = findViewById(R.id.rv_tv_series)
        progressBar = findViewById(R.id.progress_bar)
        subscriptionsContainer = findViewById(R.id.subsciptions_container)
        subscriptionsRecyclerView = findViewById(R.id.rv_subscriptions)
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(this).get(TvSeriesViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.tvSeriesList.observe(this, Observer<List<TvSerie>> { list ->
            list.let {
                viewModel.genresList.value?.let { genresList ->
                    if(tvSeriesAdapter == null) {
                        loadTvSeriesList(it, genresList)
                    } else {
                        tvSeriesAdapter?.addData(it)
                    }
                }
            }
        })

        viewModel.genresList.observe(this, Observer<List<Genre>> {
            getTvSeries(page)
        })

        viewModel.subscriptionList?.observe(this, Observer<List<SubscriptionEntity>> {
            if(!it.isNullOrEmpty()) {
                loadSubscriptionsList(it)
                subscriptionsContainer?.visibility = View.VISIBLE
            } else {
                subscriptionsContainer?.visibility = View.GONE
            }
        })
    }

    private fun addListeners() {
        tvSeriesRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    page++
                    val nextPage = page
                    getTvSeries(nextPage)
                }
            }
        })
    }

    private fun getTvSeriesGenres() {
        viewModel.getTvSeriesGenres()
    }

    private fun getTvSeries(page: Int) {
        viewModel.getTvSeries(page)
    }

    private fun loadTvSeriesList(list: List<TvSerie>, genreList: List<Genre>) {
        tvSeriesAdapter = TvSeriesAdapter(list.toMutableList(), genreList, this)
        tvSeriesRecyclerView?.layoutManager = LinearLayoutManager(this)
        tvSeriesRecyclerView?.adapter = tvSeriesAdapter

        progressBar?.visibility = View.GONE
    }

    private fun loadSubscriptionsList(list: List<SubscriptionEntity>) {
        subscriptionsAdapter = SubscriptionsAdapter(list.toMutableList(), this)
        subscriptionsRecyclerView?.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        subscriptionsRecyclerView?.adapter = subscriptionsAdapter
    }

    private fun goToSerieDetail(serie: TvSerie) {
        val intent = Intent(this, TVSerieDetailActivity::class.java)
        intent.putExtra("EXTRA_SERIE", serie)
        startActivity(intent)
    }

    override fun onSerieClicked(item: TvSerie) {
        goToSerieDetail(item)
    }

    override fun onSubscriptionClicked(item: SubscriptionEntity) {
        val tvSerie = TvSerie(item.serieId, item.name, listOf(item.genreId), item.overview, item.firstAirDate, item.posterPath)
        goToSerieDetail(tvSerie)
    }

}