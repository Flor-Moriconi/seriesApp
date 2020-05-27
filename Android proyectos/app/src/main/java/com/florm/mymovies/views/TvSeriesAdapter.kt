package com.florm.mymovies.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.florm.mymovies.IMAGE_BASE_URL
import com.florm.mymovies.R
import com.florm.mymovies.models.Genre
import com.florm.mymovies.models.TvSerie

class TvSeriesAdapter(private var list: List<TvSerie>, private var genreList: List<Genre>, private var listener: TvSerieActivityBridge): RecyclerView.Adapter<TvSeriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tv_serie, parent, false))
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.title?.text = item.name

        item.genreIds?.let { list ->
            holder.genre?.text = getGenreName(list.first())
        }

        holder.poster?.let {
            if(item.posterPath != null) {

                val urlString = IMAGE_BASE_URL + item.posterPath

                Glide.with(holder.itemView.context)
                    .load(urlString)
                    .into(it)
            }
        }

        holder.container?.setOnClickListener {
            listener.onSerieClicked(item)
        }
    }

    private fun getGenreName(genreId: String): String {
        var name = ""
        val genreMatched = genreList.find {
            it.id.equals(genreId)
        }

        genreMatched?.name?.let {
            name = it
        }

        return name
    }

    // Hacemos la inner class, para poder recibir la view, y asi bindear los datos con la pantalla.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView? = itemView.findViewById(R.id.tv_serie_title)
        var genre: TextView? = itemView.findViewById(R.id.tv_genre)
        var container: RelativeLayout? = itemView.findViewById(R.id.container)
        var poster: ImageView? = itemView.findViewById(R.id.iv_poster)
    }

    interface TvSerieActivityBridge {
        fun onSerieClicked(item: TvSerie)
    }

}