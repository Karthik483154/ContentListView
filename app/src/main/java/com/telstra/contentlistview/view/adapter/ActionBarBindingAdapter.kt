package com.telstra.contentlistview.view.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Created By Karthikeyan 08/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 * This is binding adapter class which used to bind the action bar title
 */

object ActionBarBindingAdapter {
    @JvmStatic
    @BindingAdapter("actionTitle")
    fun setActionBarTitle(textView: TextView?, actionTitle: String?) {
        textView?.text = actionTitle
    }
}