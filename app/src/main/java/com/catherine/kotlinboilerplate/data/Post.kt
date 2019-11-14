package com.catherine.kotlinboilerplate.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class Post(
    @PrimaryKey
    var id: Int,
    var title: String,
    var url: String?,
    var albumId: Int?,
    var thumbnailUrl: String?
) : Comparable<Post> {
    override fun compareTo(other: Post) = id.compareTo(other.id)
}