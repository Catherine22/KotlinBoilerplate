package com.catherine.kotlinboilerplate.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class Album(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var artist: String,
    var url: String?,
    var image: String?,
    var thumbnail_image: String?
) : Comparable<Album> {
    override fun compareTo(other: Album) = when {
        this.artist == other.artist && this.title == other.title -> 0
        this.artist == other.artist && this.title != other.title -> this.title.compareTo(other.title)
        else -> this.artist.compareTo(other.artist)
    }
}