package com.telstra.contentlistview.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.telstra.contentlistview.R
import com.telstra.contentlistview.model.UserContentRows

/**
 * Created by Karthikeyan 07/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 * This is class acts as a bridge between a RecyclerView and the underlying data for that view.
 * Responsible for making a View for each item in the data set
 */

class ContentAdapter(

    private val mContext: Context,
    private var userContentRowList: List<UserContentRows>
) :
    RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    /**
     * This method is returning the view for each item in the list
     *
     * @parent - View Group
     * @viewType - View Type
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_layout, parent, false))
    }

    /**
     * This method is giving the size of the list
     */
    override fun getItemCount(): Int {
        return userContentRowList.size
    }

    /**
     * This method is binding the data on the list
     *
     * @holder - View Holder
     * @position - List position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userContentRow = userContentRowList[position]

        holder.itemTitle.text = userContentRow.contentTitle ?: mContext.getString(R.string.app_default_title)
        holder.itemDescription.text = userContentRow.contentDescription ?: mContext.getString(R.string.app_default_description)

        //Load the image with Picasso
        Picasso.get().load(userContentRow.contentImageHref)
            .resize(300, 300)
            .onlyScaleDown()
            .into(holder.itemImage)
    }

    /**
     * This class is holding the list view
     *
     * @itemView - Item View
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
        var itemDescription: TextView = itemView.findViewById(R.id.itemDescription)
        var itemImage: ImageView = itemView.findViewById(R.id.itemImage)

    }
}