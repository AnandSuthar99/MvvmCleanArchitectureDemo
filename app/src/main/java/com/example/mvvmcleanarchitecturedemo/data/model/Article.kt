package com.example.mvvmcleanarchitecturedemo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: Date?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable {

    fun getFormattedPublishedAt(): String {
        try {
            val toFormat = SimpleDateFormat("hh:mm a - MMM dd,yyyy ")
//            val toFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
            toFormat.timeZone = TimeZone.getDefault()
            publishedAt?.let {
                return toFormat.format(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return publishedAt.toString()
    }
}