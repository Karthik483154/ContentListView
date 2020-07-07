package com.telstra.contentlistview.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Created by Karthikeyan 07/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 * This is class to check whether network connection is available or not before retrieving server.
 */

class NetworkConnectionHandler {

    companion object {
        /**
         * This method used to check the network connection status such as WIFI, ETHERNET and CELLULAR
         *
         * @context - Application context
         */
        fun isNetworkConnectionAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        }
    }
}