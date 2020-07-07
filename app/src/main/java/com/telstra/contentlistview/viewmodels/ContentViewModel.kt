package com.telstra.contentlistview.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.telstra.contentlistview.model.UserContent
import com.telstra.contentlistview.model.UserContentApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Karthikeyan 06/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 *  This is View Model Component class for to get the JSON data from URL with using Retrofit client and we are using MutableLiveData and
 *  LiveData classes for the storing the list and the rest is managed by ViewModel component
 */

class ContentViewModel : ViewModel() {

    //This is the data that we will fetch asynchronously
    private var userContentDataList: MutableLiveData<UserContent?>? = null

    //Call this method to get the data
    val userContentData: LiveData<UserContent?>
        get() {
            //If the list is null
            if (userContentDataList == null) {
                userContentDataList = MutableLiveData()
                //Load it asynchronously
                loadUserContentData()
            }
            return userContentDataList!!
        }

    /**
     * This method is using Retrofit to get the JSON data from URL
     */
    private fun loadUserContentData() {
        val retrofitClient = Retrofit.Builder()
            .baseUrl(UserContentApiService.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userContentApiService = retrofitClient.create(UserContentApiService::class.java)
        val call = userContentApiService.getUserContentData()
        call.enqueue(object : Callback<UserContent?> {
            override fun onResponse(call: Call<UserContent?>, response: Response<UserContent?>) {
                userContentDataList!!.value = response.body()
            }

            override fun onFailure(call: Call<UserContent?>, t: Throwable) {
                userContentDataList!!.value = call.execute().body()
            }
        })
    }
}