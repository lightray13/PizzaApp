package com.pizza.application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pizza.application.R
import com.pizza.application.util.ImageLoader
import kotlinx.android.synthetic.main.banner_item.view.*

class BannerAdapter: RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {
    private val bannerList = arrayOf(R.drawable.banner1, R.drawable.banner3, R.drawable.banner2)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    class BannerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: Int) {
            ImageLoader.loadBanner(itemView.bannerItemImageView, model)
        }
    }
}