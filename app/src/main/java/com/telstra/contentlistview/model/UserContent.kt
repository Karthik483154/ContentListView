package com.telstra.contentlistview.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Karthikeyan 06/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 * This is a class that will hold the user content data. Here we used to Gson converter and so the JSON response is
 * automatically converted to the respective and the converter will compare the response tree with the serialized name.
 */

data class UserContent(

    @SerializedName("title")
    val actionTitle: String,
    @SerializedName("rows")
    val userContentRows: List<UserContentRows>
)