package com.catherine.kotlinboilerplate

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.catherine.kotlinboilerplate.data.AlbumDao
import com.catherine.kotlinboilerplate.data.AlbumDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AlbumEntityReadWriteTest {
    private lateinit var mDao: AlbumDao
    private lateinit var mDB: AlbumDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mDB = Room.inMemoryDatabaseBuilder(context, AlbumDatabase::class.java).build()
        mDao = mDB.albumDao()
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDao.deleteAll()
        mDB.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val albums = TestUtils.createRandomAlbums(5).sorted()
        mDao.insertAll(albums)
        val retrievedData = mDao.getRawList().sorted()

        // verify data
        assertEquals(albums.size, retrievedData.size)
        retrievedData.forEachIndexed { i, v ->
            assertEquals(v.compareTo(albums[i]), 0)
        }
    }
}
