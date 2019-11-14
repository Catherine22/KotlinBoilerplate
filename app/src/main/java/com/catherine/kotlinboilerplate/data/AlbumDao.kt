package com.catherine.kotlinboilerplate.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery


@Dao
interface AlbumDao {
    @Query("SELECT * FROM album ORDER BY title ASC")
    fun getAll(): LiveData<List<Album>>

    @Query("SELECT * FROM album ORDER BY title ASC")
    fun getAlbumDataSource(): DataSource.Factory<Int, Album>

    @Query("SELECT * FROM album ORDER BY title ASC")
    fun getRawList(): List<Album>

    @Query("SELECT * FROM album ORDER BY RANDOM() LIMIT :limit")
    fun getRandom(limit: Int): LiveData<List<Album>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<Album>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Album: Album)

    @Transaction
    fun updateAll(albums: List<Album>) {
        deleteAll()
        insertAll(albums)
    }

    @Transaction
    fun update(newAlbum: Album, oldAlbum: Album) {
        insert(newAlbum)
        deleteByRawQuery(oldAlbum.id)
    }

    @Query("SELECT COUNT(:columnName) FROM album")
    fun getRawCount(columnName: String): LiveData<Int>


    @RawQuery
    fun updateByRawQuery(query: SupportSQLiteQuery): Int

    @Query("DELETE FROM album WHERE id = :id")
    fun deleteByRawQuery(id: Int): Int

    @Query("DELETE FROM album")
    fun deleteAll()
}