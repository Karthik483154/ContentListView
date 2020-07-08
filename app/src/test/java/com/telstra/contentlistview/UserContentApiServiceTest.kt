package com.telstra.contentlistview

import com.telstra.contentlistview.service.repository.UserContentApiService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class UserContentApiServiceTest {

    private var mockWebServer: MockWebServer = MockWebServer()

    private lateinit var apiService: UserContentApiService

    @Before
    fun setup() {
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(UserContentApiService.baseURL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserContentApiService::class.java)

    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testAppVersion() {
        //Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{}")

        mockWebServer.enqueue(response)
    }
}