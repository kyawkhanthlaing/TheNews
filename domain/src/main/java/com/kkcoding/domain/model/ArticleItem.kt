package com.kkcoding.domain.model

import android.os.Build
import com.kkcoding.data.db.entities.ArticleItemEntity
import com.kkcoding.data.response.article.ArticleItemResponse
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


data class ArticleItem(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val isSaved: Boolean = false
) : Serializable

fun ArticleItemResponse.mapToDomain() = ArticleItem(
    author.orEmpty(),
    content.orEmpty(),
    description.orEmpty(),
    publishedAt.orEmpty().formatTimeAgo(),
    title.orEmpty(),
    url.orEmpty(),
    urlToImage.orEmpty(),
    isSaved = false
)

fun String.formatTimeAgo(): String {

    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX").withZone(ZoneOffset.UTC)
            val date = fmt.parse(this)
            val time = Instant.from(date)
            val now = Instant.now(Clock.systemUTC())

            val duration = Duration.between(time, now)
            val minDiff = duration.toMinutes()

            val fmtOut = DateTimeFormatter.ofPattern("dd-mm-yyyy hh:mm:ss").withZone(ZoneOffset.UTC)
            val fallBackString = fmtOut.format(time)

            if (minDiff > 1440) {
                val dayAgo = minDiff / 1440
                return dayAgo.toInt().toString().plus(" day ago")
            } else if (minDiff > 60) {
                val hour = minDiff / 60
                return hour.toInt().toString().plus(" hours ago")
            } else if (minDiff > 5) {
                return minDiff.toInt().toString().plus(" mins ago")
            } else if (minDiff > 0) {
                return "Just now"
            }

            return fallBackString
        } else {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
            val formattedDate = formatter.format(parser.parse("2018-12-14T09:55:00"))
            return formattedDate.toString()
        }

    } catch (e: Exception) {
        ""
    }

}

fun ArticleItemEntity.mapToDomain() = ArticleItem(
    author, content, description, publishedAt, title, url, urlToImage, isSaved = true
)

fun ArticleItem.mapToEntity() = ArticleItemEntity(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage
)