package com.telstra.contentlistview.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.telstra.contentlistview.R
import com.telstra.contentlistview.databinding.ListLayoutBinding
import com.telstra.contentlistview.service.model.UserContentRows

/**
 * Created by Karthikeyan 07/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 * This is class acts as a bridge between a RecyclerView and the underlying data for that view.
 * Responsible for making a View for each item in the data set
 */

class ContentAdapter : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    private var userContentRowList: List<UserContentRows?>? = null

    /**
     * This is method used to instance the user content list if value is null, if value is loaded
     * that can be fetch from old list and also update the data from new list
     */
    fun setContentList(userContentRowList: List<UserContentRows?>) {
        if (this.userContentRowList == null) {
            this.userContentRowList = userContentRowList
            notifyItemRangeChanged(0, userContentRowList.size)
        } else {
            val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@ContentAdapter.userContentRowList!![oldItemPosition]?.contentTitle ===
                            userContentRowList[newItemPosition]?.contentTitle
                }

                override fun getOldListSize(): Int {
                    return this@ContentAdapter.userContentRowList!!.size
                }

                override fun getNewListSize(): Int {
                    return userContentRowList.size
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val userContent: UserContentRows? = userContentRowList[newItemPosition]
                    val oldUserContent: UserContentRows? = userContentRowList[oldItemPosition]

                    return (userContent?.contentTitle == oldUserContent?.contentTitle &&
                            userContent?.contentDescription == oldUserContent?.contentDescription)
                }
            })
            this.userContentRowList = userContentRowList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.content = userContentRowList?.get(position)
        holder.binding.executePendingBindings()
    }

    /**
     * This method is giving the size of the list
     */
    override fun getItemCount(): Int {
        return userContentRowList?.size ?: 0
    }

    /**
     * This class is holding the list view
     *
     * @itemView - Item View
     */
    class ViewHolder(val binding: ListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}