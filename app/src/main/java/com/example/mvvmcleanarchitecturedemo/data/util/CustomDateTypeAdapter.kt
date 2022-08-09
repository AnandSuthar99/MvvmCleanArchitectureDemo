package com.example.mvvmcleanarchitecturedemo.data.util

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object CustomDateTypeAdapter : JsonDeserializer<Date> {

    private val DATE_FORMATS = arrayOf("yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd")
    private val dateFormatters: List<SimpleDateFormat> =
        DATE_FORMATS.map { SimpleDateFormat(it, Locale.US) }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date {
        for (formatter in dateFormatters) {
            try {
                formatter.timeZone = TimeZone.getTimeZone("UTC")
                return formatter.parse(json?.asString)
            } catch (ignore: ParseException) {
                Log.e("Exception", ignore.message.toString())
            }
        }

        throw JsonParseException("DateParseException: " + json.toString())
    }
}