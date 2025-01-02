package net.developermaster.timejob.model

import androidx.collection.longListOf

data class Movie(
    val id: Int,
    val title: String,
    val poster: String,
)

val movies = (101..201).map {
    Movie(
        id = it,
        title = "$it",
        poster = "https://picsum.photos/200/300?id=$it",
    )
}

