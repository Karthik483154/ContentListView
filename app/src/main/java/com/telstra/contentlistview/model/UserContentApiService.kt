package com.telstra.contentlistview.model

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Karthikeyan 06/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 * This is a interface that lets Retrofit connect to the API service. Here we makes http request to connect with given base url
 */

interface UserContentApiService {

    //This companion object is initialized when the class is loaded
    companion object {
        const val baseURL: String =
            "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"
    }

    //Makes http get request
    @GET("facts")
    fun getUserContentData(): Call<UserContent>
}