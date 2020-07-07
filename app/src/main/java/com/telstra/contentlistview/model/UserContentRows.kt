package com.telstra.contentlistview.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Karthikeyan 06/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 * This is a class that will hold the user content rows data. Here we used to Gson converter and so the JSON response is
 * automatically converted to the respective and the converter will compare the response tree with the serialized name.
 */

data class UserContentRows(
    @SerializedName("title")
    val contentTitle: String,
    @SerializedName("description")
    val contentDescription: String,
    @SerializedName("imageHref")
    val contentImageHref: String
)