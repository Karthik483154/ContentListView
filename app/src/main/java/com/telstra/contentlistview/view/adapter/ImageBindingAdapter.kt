package com.telstra.contentlistview.view.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

/**
 * Created By Karthikeyan 08/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 * This is the binding adapter class which used to bind the image url and display on image view with help of Picasso
 */

object ImageBindingAdapter {
    /**
     * This is method used to load the image with Picasso
     *
     * @imageView - Image View object
     * @imageUrl - Binding image url
     */
    @JvmStatic
    @BindingAdapter("contentImageHref")
    fun setImageUrl(imageView: ImageView?, imageUrl: String?) {
        //Load the image with Picasso
        if (!imageUrl.isNullOrEmpty())
            Picasso.get().load(imageUrl)
                .resize(300, 300)
                .onlyScaleDown()
                .into(imageView)
    }
}