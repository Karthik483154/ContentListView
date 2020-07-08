package com.telstra.contentlistview.service.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.telstra.contentlistview.service.model.UserContent
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
 *  This is Repository class for to get the JSON data from URL with using Retrofit client and we are using MutableLiveData and
 *  LiveData classes for the storing the list
 */

class UserContentRepository private constructor() {

    //API service instance
    private var apiService: UserContentApiService

    /**
     * This init invoke Retrofit API to get the JSON data from URL
     */
    init {
        val builder = GsonBuilder()
        val deserializerContent = GetContentListDeserializer()
        builder.registerTypeAdapter(UserContent::class.java, deserializerContent)
        val converterFactory = builder.create()

        val retrofitClient = Retrofit.Builder()
            .baseUrl(UserContentApiService.baseURL)
            .addConverterFactory(GsonConverterFactory.create(converterFactory))
            .build()

        apiService = retrofitClient.create(UserContentApiService::class.java)
    }

    //Class Instance creation - Singleton class
    companion object {
        private var projectRepository: UserContentRepository? = null

        @get:Synchronized
        val instance: UserContentRepository?
            get() {
                if (projectRepository == null) {
                    projectRepository = UserContentRepository()
                }
                return projectRepository
            }
    }

    /**
     * This is method used to get data that we will fetch asynchronously
     */
    fun getUserContentDetails(): LiveData<UserContent> {
        val userContentData: MutableLiveData<UserContent> = MutableLiveData()
        val contentData = apiService.getUserContentDetails()
        contentData.enqueue(object : Callback<UserContent?> {
            override fun onResponse(call: Call<UserContent?>, response: Response<UserContent?>) {
                userContentData.value = response.body()
            }

            override fun onFailure(call: Call<UserContent?>, t: Throwable) {
                userContentData.value = null
            }
        })
        return userContentData
    }
}