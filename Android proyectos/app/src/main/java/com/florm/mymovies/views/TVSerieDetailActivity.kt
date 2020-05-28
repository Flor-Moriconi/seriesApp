package com.florm.mymovies.views

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.florm.mymovies.IMAGE_BASE_URL
import com.florm.mymovies.R
import com.florm.mymovies.models.TvSerie
import com.florm.mymovies.viewmodels.TvSerieDetailViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import org.joda.time.LocalDate
import android.animation.ObjectAnimator
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.florm.mymovies.database.entities.SubscriptionEntity

class TVSerieDetailActivity : AppCompatActivity() {

    private var backArrowImageButton: ImageButton? = null
    private var titleTextView : TextView? = null
    private var releaseYearTextView: TextView? = null
    private var overviewTextView: TextView? = null
    private var posterImageView: ImageView? = null
    private var subscribeButton: Button? = null
    private var coordinatorLayout: CoordinatorLayout? = null
    private var collapsingToolbar: CollapsingToolbarLayout? = null
    private var appBarLayout: AppBarLayout? = null
    private var imageCardView: CardView? = null

    private var scrollView: NestedScrollView? = null
    private lateinit var viewModel: TvSerieDetailViewModel

    private var backgroundDominantColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_serie_detail)

        bindViewElements()
        addListeners()
        createViewModel()
        addObservers()
        getIntentData()
    }

    private fun bindViewElements() {
        backArrowImageButton = findViewById(R.id.id_back_arrow)
        titleTextView = findViewById(R.id.tv_title)
        releaseYearTextView = findViewById(R.id.tv_release_year)
        overviewTextView = findViewById(R.id.tv_description)
        posterImageView = findViewById(R.id.iv_poster)
        subscribeButton = findViewById(R.id.btn_subscribe)
        coordinatorLayout = findViewById(R.id.coordinator_layout)
        collapsingToolbar = findViewById(R.id.collapsing_toolbar)
        appBarLayout = findViewById(R.id.app_bar_layout)
        imageCardView = findViewById(R.id.poster_cardview)
        scrollView = findViewById(R.id.scroll_view)
    }

    private fun addListeners() {
        backArrowImageButton?.setOnClickListener {
            goBackActivity()
        }

        appBarLayout?.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, _ ->
            val offsetAlpha = appBarLayout.y / appBarLayout.totalScrollRange
            val scaleDownX = ObjectAnimator.ofFloat(imageCardView, "scaleX", offsetAlpha + 1)
            val scaleDownY = ObjectAnimator.ofFloat(imageCardView, "scaleY", offsetAlpha + 1)
            scaleDownX.setDuration(0).start()
            scaleDownY.setDuration(0).start()
        })

        subscribeButton?.setOnClickListener {
            if(viewModel.isSubscribed()) {
                viewModel.deleteSubscription()
                setDesignButtonToSubscribe()
            } else {
                viewModel.makeSubscription()
                setDesignButtonToIsSubscribed()
            }
        }
    }

    private fun setDesignButtonToSubscribe() {
        subscribeButton?.setBackgroundResource(R.drawable.button_border_round)
        subscribeButton?.setText(R.string.subscribe_button_text)
        subscribeButton?.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun setDesignButtonToIsSubscribed() {
        subscribeButton?.setBackgroundResource(R.drawable.button_subscribed_border_round)
        subscribeButton?.setText(R.string.title_subscripted)
        subscribeButton?.setTextColor(backgroundDominantColor)
    }

    private fun createViewModel() {
        viewModel =  ViewModelProvider(this).get(TvSerieDetailViewModel::class.java)
    }

    private fun goBackActivity() {
        finish()
    }

    private fun getIntentData() {
        viewModel.serieDetails  = intent.getSerializableExtra("EXTRA_SERIE") as TvSerie
        loadSerieDetails()
    }

    private fun loadSerieDetails() {
        viewModel.serieDetails.let {
            titleTextView?.text = it.name
            overviewTextView?.text = it.overview

            it.firstAirDate?.let { date ->
                releaseYearTextView?.text = getReleaseYear(date)
            }

            it.posterPath?.let { posterPath ->
                loadImageWithGlade(posterPath)
            }
        }
    }

    private fun getReleaseYear(releaseDate: String): String {
        val date = LocalDate.parse(releaseDate)
        return date.year.toString()
    }

    private fun addObservers() {
        viewModel.subscriptionsList?.observe(this, Observer<List<SubscriptionEntity>> {
            it.let {
                if(viewModel.isSubscribed()) {
                    setDesignButtonToIsSubscribed()
                } else {
                    setDesignButtonToSubscribe()
                }
            }
        })
    }

    private fun loadImageWithGlade(posterPath: String) {
        val urlString = IMAGE_BASE_URL + posterPath

        posterImageView?.let { imageView ->
            Glide.with(this.applicationContext)
                .asBitmap()
                .load(urlString)
                .apply(RequestOptions().error(R.drawable.test_image_d))
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        onPalette(Palette.from(resource!!).generate())
                        imageView.setImageBitmap(resource)
                        return false
                    }

                    fun onPalette(palette: Palette?) {
                        if (null != palette) {
                            if (palette.dominantSwatch != null) {
                                backgroundDominantColor = palette.dominantSwatch!!.rgb
                                coordinatorLayout?.setBackgroundColor(palette.dominantSwatch!!.rgb)
                                collapsingToolbar?.setBackgroundColor(palette.dominantSwatch!!.rgb)

                            } else {
                                if(palette.vibrantSwatch != null) { // Just in case that the library doesn't found the dominant color.
                                    backgroundDominantColor = palette.vibrantSwatch!!.rgb
                                    coordinatorLayout?.setBackgroundColor(palette.vibrantSwatch!!.rgb)
                                    collapsingToolbar?.setBackgroundColor(palette.vibrantSwatch!!.rgb)
                                }
                            }
                        }

                        // Set color to the text
                        if(viewModel.isSubscribed()) {
                            subscribeButton?.setTextColor(backgroundDominantColor)
                        }
                    }
                }).into(imageView)
        }
    }
}