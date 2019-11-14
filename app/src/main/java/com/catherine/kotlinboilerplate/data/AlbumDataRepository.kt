package com.catherine.kotlinboilerplate.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.catherine.kotlinboilerplate.httpClient.HttpClient
import com.catherine.kotlinboilerplate.httpClient.RallyCodingService
import com.catherine.kotlinboilerplate.utilities.CLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class AlbumDataRepository(private val mDao: AlbumDao, private val mIoExecutor: ExecutorService) {

    fun requestAlbums() {
        val request =
            HttpClient.instance.rallyCodingBaseRequest.create(RallyCodingService::class.java)
        val call = request.getAlbums()
        call.enqueue(object : Callback<List<Album>> {
            override fun onResponse(
                call: Call<List<Album>>,
                response: Response<List<Album>>
            ) {
                CLog.d(
                    "${response.isSuccessful}, ${response.message()}, ${response.body()}"
                )
                if (response.isSuccessful && response.body() != null) {
                    sInstance?.mIoExecutor?.submit { mDao.updateAll(response.body()!!) }
                }
            }

            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getAlbums(size: Int): LiveData<PagedList<Album>> {
        return LivePagedListBuilder(
            mDao.getAlbumDataSource(), /* page size */ size
        ).build()
    }

    fun getRandomAlbums(limit: Int): LiveData<List<Album>>? {
        return try {
            sInstance?.mIoExecutor?.submit<LiveData<List<Album>>> { mDao.getRandom(limit) }?.get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            null
        } catch (e: ExecutionException) {
            e.printStackTrace()
            null
        }

    }

    fun getAllAlbums(): LiveData<List<Album>>? {
        return try {
            sInstance?.mIoExecutor?.submit(mDao::getAll)?.get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            null
        } catch (e: ExecutionException) {
            e.printStackTrace()
            null
        }

    }

    fun insert(album: Album) {
        mIoExecutor.submit { mDao.insert(album) }
    }

    fun getAlbumCount(): LiveData<Int>? {
        return try {
            sInstance?.mIoExecutor?.submit<LiveData<Int>> {
                mDao.getRawCount("title")
            }?.get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            null
        } catch (e: ExecutionException) {
            e.printStackTrace()
            null
        }
    }

    fun getArtistCount(): LiveData<Int>? {
        return try {
            sInstance?.mIoExecutor?.submit<LiveData<Int>> {
                mDao.getRawCount("artist")
            }?.get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            null
        } catch (e: ExecutionException) {
            e.printStackTrace()
            null
        }
    }


    companion object {
        @Volatile
        private var sInstance: AlbumDataRepository? = null

        fun getInstance(application: Application): AlbumDataRepository {
            if (sInstance == null) {
                synchronized(AlbumDataRepository::class.java) {
                    if (sInstance == null) {
                        val database = AlbumDatabase.getInstance(application)
                        sInstance = AlbumDataRepository(
                            database!!.albumDao(),
                            Executors.newSingleThreadExecutor()
                        )

                        val executor = Executors.newSingleThreadExecutor()
                        executor.submit(sInstance!!::requestAlbums)
                    }
                }
            }
            return sInstance!!
        }
    }
}