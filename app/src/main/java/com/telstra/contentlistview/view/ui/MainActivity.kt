package com.telstra.contentlistview.view.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.telstra.contentlistview.R
import com.telstra.contentlistview.databinding.ActivityMainBinding
import com.telstra.contentlistview.service.model.UserContent
import com.telstra.contentlistview.util.NetworkConnectionHandler
import com.telstra.contentlistview.view.adapter.ContentAdapter
import com.telstra.contentlistview.viewmodel.ContentListViewModel
import org.jetbrains.annotations.TestOnly

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

    //RecyclerView Adapter
    private lateinit var contentAdapter: ContentAdapter

    private lateinit var activityBinding: ActivityMainBinding

    private lateinit var userContentModel: ContentListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()
        //Initialize the UI Component
        initView()
        //Calling API service to get the data from feed
        loadDataModel()
    }

    /**
     * This method used to initialize the UI components from the main activity
     */
    private fun initView() {
        //Setting up the adapter
        contentAdapter = ContentAdapter()
        activityBinding.contentRecyclerView.adapter = contentAdapter

        //Setting up the swipe refresh layout
        activityBinding.swipeContentView.setColorSchemeColors(Color.WHITE)
        activityBinding.swipeContentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        activityBinding.swipeContentView.setOnRefreshListener {
            loadDataModel()
        }
        activityBinding.isProgressLoading = true
        activityBinding.isShowContent = true
    }

    /**
     * This method used to interact with View model to obtain the data from the model class.
     * Here ContentViewModel is ViewModel and MainActivity class act as a View
     */
    private fun loadDataModel() {
        //Check the network connection status
        if (NetworkConnectionHandler.isNetworkConnectionAvailable(this)) {
            userContentModel = ViewModelProviders.of(this).get(ContentListViewModel::class.java)
            contentObserverViewModel(userContentModel)
        } else {
            activityBinding.isProgressLoading = false
            activityBinding.isShowContent = false
            activityBinding.swipeContentView.isRefreshing = false
            activityBinding.contentErrorMessage.text = getString(R.string.app_no_internet_message)
            Toast.makeText(this, R.string.app_no_internet_message, Toast.LENGTH_LONG).show()
        }
    }

    private fun contentObserverViewModel(userContentModel: ContentListViewModel) {
        userContentModel.getContentListObservable().observe(this, Observer { userContentData: UserContent? ->
            activityBinding.isProgressLoading = false
            activityBinding.swipeContentView.isRefreshing = false

            //If the list is null
            if (userContentData == null) {
                activityBinding.isShowContent = false
            } else {
                activityBinding.isShowContent = true

                activityBinding.content = userContentData

                if (userContentData.userContentRows.isNotEmpty()) {
                    contentAdapter.setContentList(userContentData.userContentRows)
                } else
                    activityBinding.isShowContent = false
            }
        })
    }


    @TestOnly
    fun setTestViewModel(testContentViewModel: ContentListViewModel) {
        userContentModel = testContentViewModel
    }
}
