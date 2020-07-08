package com.telstra.contentlistview.service.repository

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.telstra.contentlistview.service.model.UserContent
import com.telstra.contentlistview.service.model.UserContentRows
import java.lang.reflect.Type

/**
 * Created by Karthikeyan 08/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 * This is customize class which used to deserialize the json object once server feed the data to the repository api
 */

class GetContentListDeserializer : JsonDeserializer<UserContent> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): UserContent? {
        val userContentRowList: MutableList<UserContentRows> = ArrayList()

        val jsonObject = json.asJsonObject
        var title = ""
        if (!jsonObject["title"].isJsonNull)
            title = jsonObject["title"].asString

        val rows = jsonObject["rows"].asJsonArray

        if (rows != null) {
            for (itemJsonElement in rows) {
                val itemJsonObject = itemJsonElement.asJsonObject
                var contentTitle = ""
                var contentDescription = ""
                var contentImageHref = ""
                if (!(itemJsonObject["title"].isJsonNull && itemJsonObject["description"].isJsonNull && itemJsonObject["imageHref"].isJsonNull)) {
                    if (!itemJsonObject["title"].isJsonNull)
                        contentTitle = itemJsonObject["title"].asString
                    if (!itemJsonObject["description"].isJsonNull)
                        contentDescription = itemJsonObject["description"].asString
                    if (!itemJsonObject["imageHref"].isJsonNull)
                        contentImageHref = itemJsonObject["imageHref"].asString
                    userContentRowList.add(UserContentRows(contentTitle, contentDescription, contentImageHref))
                }
            }
        }
        return UserContent(title, userContentRowList)
    }
}