package com.telstra.contentlistview.view.adapter

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by Karthikeyan 08/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 * This is the binding adapter class which used to bind the status of ui visibility mode
 */

object UIVisibilityBindingAdapter {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showProgress(view: View?, isShow: Boolean) {
        view?.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleError")
    fun showError(view: View?, isShow: Boolean) {
        view?.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}