package com.pizza.application.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pizza.application.R

object ImageLoader {
    fun loadImage(view: ImageView, url: String, placeholder: Int = R.drawable.ic_image) {
        Glide.with(view)
            .load(url)
            .placeholder(placeholder)
            .error(placeholder)
            .fallback(placeholder)
            .into(view)
    }

    fun loadBanner(view: ImageView, resource: Int) {
        Glide.with(view)
            .load(resource)
            .into(view)
    }
}