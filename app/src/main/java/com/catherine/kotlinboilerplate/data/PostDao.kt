package com.catherine.kotlinboilerplate.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery


@Dao
interface PostDao {
    @Query("SELECT * FROM post ORDER BY id ASC")
    fun getAll(): LiveData<List<Post>>

    @Query("SELECT * FROM post ORDER BY id ASC")
    fun getPostDataSource(): DataSource.Factory<Int, Post>

    @Query("SELECT * FROM post ORDER BY id ASC")
    fun getRawList(): List<Post>

    @Query("SELECT * FROM post ORDER BY RANDOM() LIMIT :limit")
    fun getRandom(limit: Int): LiveData<List<Post>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Post: Post)

    @Transaction
    fun updateAll(posts: List<Post>) {
        deleteAll()
        insertAll(posts)
    }

    @Transaction
    fun update(newPost: Post, oldPost: Post) {
        insert(newPost)
        deleteByRawQuery(oldPost.id)
    }

    @Query("SELECT COUNT(id) FROM post")
    fun getRawCount(): LiveData<Int>

    @RawQuery
    fun updateByRawQuery(query: SupportSQLiteQuery): Int

    @Query("DELETE FROM post WHERE id = :id")
    fun deleteByRawQuery(id: Int): Int

    @Query("DELETE FROM post")
    fun deleteAll()
}