package com.florm.mymovies.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.florm.mymovies.IMAGE_BASE_URL
import com.florm.mymovies.R
import com.florm.mymovies.database.entities.SubscriptionEntity

class SubscriptionsAdapter(private var list: MutableList<SubscriptionEntity>, private var listener: SubscriptionsBridge) : RecyclerView.Adapter<SubscriptionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_subscription, parent, false))
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.poster?.let {
            if(item.posterPath != null) {

                val urlString = IMAGE_BASE_URL + item.posterPath

                Glide.with(holder.itemView.context)
                    .load(urlString)
                    .into(it)
            }
        }

        holder.container?.setOnClickListener {
            listener.onSubscriptionClicked(item)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var poster: ImageView? = itemView.findViewById(R.id.iv_image)
        var container: RelativeLayout? = itemView.findViewById(R.id.container_view)
    }

    interface SubscriptionsBridge {
        fun onSubscriptionClicked(item: SubscriptionEntity)
    }

}