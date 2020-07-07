package com.telstra.contentlistview.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.telstra.contentlistview.R
import com.telstra.contentlistview.model.UserContent
import com.telstra.contentlistview.util.NetworkConnectionHandler
import com.telstra.contentlistview.viewmodels.ContentViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Karthikeyan 06/07/2020
 *
 * @author Karthikeyan
 * @Version 1.0
 *
 * This is an activity to shows how to display a ListView based on array data source using a
 * custom layout template for displaying each item.
 */

class MainActivity : AppCompatActivity() {

    private var userContent: UserContent? = null

    //RecyclerView Adapter
    private lateinit var contentAdapter: ContentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Initialize the UI Component
        initView()

        //Calling API service to get the data from feed
        loadDataModel()
    }

    /**
     * This method used to initialize the UI components from the main activity
     */
    fun initView() {
        //Setting up the swipe refresh layout
        swipeContentView.visibility = View.GONE
        swipeContentView.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        swipeContentView.setColorSchemeColors(Color.WHITE)
        swipeContentView.setOnRefreshListener {
            loadDataModel()
        }

        //Setting up the recyclerview
        contentRecyclerView.setHasFixedSize(true)
        contentRecyclerView.layoutManager = LinearLayoutManager(this)

        //Setting up the progress bar view
        progressBar.visibility = View.VISIBLE

        //Setting up the error message text view
        contentErrorMessage.visibility = View.GONE
    }

    /**
     * This method used to interact with View model to obtain the data from the model class.
     * Here ContentViewModel is ViewModel and MainActivity class act as a View
     */
    fun loadDataModel() {
        //Check the network connection status
        if (NetworkConnectionHandler.isNetworkConnectionAvailable(this)) {
            val userContentModel: ContentViewModel =
                ViewModelProviders.of(this).get(ContentViewModel::class.java)
            userContentModel.userContentData.observe(this, Observer { userContentData: UserContent? ->
                progressBar.visibility = View.GONE
                swipeContentView.visibility = View.VISIBLE
                swipeContentView.isRefreshing = false

                userContent = userContentData
                //If the list is null
                if (userContent == null) {
                    contentErrorMessage.visibility = View.VISIBLE
                    contentRecyclerView.visibility = View.GONE
                } else {
                    contentErrorMessage.visibility = View.GONE
                    contentRecyclerView.visibility = View.VISIBLE

                    val actionBar = supportActionBar
                    actionBar!!.title = userContent!!.actionTitle
                    contentAdapter = ContentAdapter(this, userContent!!.userContentRows)
                    contentRecyclerView.adapter = contentAdapter
                    contentAdapter.notifyDataSetChanged()
                }
            })
        } else {
            progressBar.visibility = View.GONE
            contentRecyclerView.visibility = View.GONE
            swipeContentView.isRefreshing = false
            swipeContentView.visibility = View.VISIBLE
            contentErrorMessage.visibility = View.VISIBLE
            contentErrorMessage.text = getString(R.string.app_no_internet_message)
            Toast.makeText(this, R.string.app_no_internet_message, Toast.LENGTH_LONG).show()
        }
    }
}
