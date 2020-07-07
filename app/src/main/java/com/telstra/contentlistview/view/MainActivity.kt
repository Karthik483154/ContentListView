package com.telstra.contentlistview.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.telstra.contentlistview.R
import com.telstra.contentlistview.model.UserContent
import com.telstra.contentlistview.viewmodels.ContentViewModel

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

    //Swipe Refresh Layout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    //Recycler View
    private lateinit var recyclerView: RecyclerView

    //RecyclerView Adapter
    private lateinit var contentAdapter: ContentAdapter

    //Progress bar view
    private lateinit var progressBar: ProgressBar

    //Content Error text view
    private lateinit var contentErrorMessage: TextView


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
    private fun initView() {
        //Setting up the swipe refresh layout
        swipeRefreshLayout = findViewById(R.id.swipeContentView)
        swipeRefreshLayout.visibility = View.GONE
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE)
        swipeRefreshLayout.setOnRefreshListener {
            loadDataModel()
        }

        //Setting up the recyclerview
        recyclerView = findViewById(R.id.contentRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Setting up the progress bar view
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        //Setting up the error message text view
        contentErrorMessage = findViewById(R.id.contentErrorMessage)
        contentErrorMessage.visibility = View.GONE
    }

    /**
     * This method used to interact with View model to obtain the data from the model class.
     * Here ContentViewModel is ViewModel and MainActivity class act as a View
     */
    private fun loadDataModel() {
        val userContentModel: ContentViewModel =
            ViewModelProviders.of(this).get(ContentViewModel::class.java)
        userContentModel.userContentData.observe(this, Observer { userContentData: UserContent? ->
            progressBar.visibility = View.GONE
            swipeRefreshLayout.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = false

            userContent = userContentData
            //If the list is null
            if (userContent == null) {
                contentErrorMessage.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                val actionBar = supportActionBar
                actionBar!!.title = userContent!!.actionTitle
                contentAdapter = ContentAdapter(this, userContent!!.userContentRows)
                recyclerView.adapter = contentAdapter
                contentAdapter.notifyDataSetChanged()
            }
        })
    }
}
