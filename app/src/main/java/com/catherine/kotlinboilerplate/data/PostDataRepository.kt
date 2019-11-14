package com.catherine.kotlinboilerplate.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.catherine.kotlinboilerplate.httpClient.HttpClient
import com.catherine.kotlinboilerplate.httpClient.JSONPlaceholderService
import com.catherine.kotlinboilerplate.utilities.CLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class PostDataRepository(private val mDao: PostDao, private val mIoExecutor: ExecutorService) {

    fun requestPosts() {
        val request =
            HttpClient.instance.jsonPlaceholderRequest.create(JSONPlaceholderService::class.java)
        val call = request.getPosts()
        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {
                CLog.d(
                    "${response.isSuccessful}, ${response.message()}, ${response.body()}"
                )
                if (response.isSuccessful && response.body() != null) {
                    sInstance?.mIoExecutor?.submit { mDao.updateAll(response.body()!!) }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getPosts(size: Int): LiveData<PagedList<Post>> {
        return LivePagedListBuilder(
            mDao.getPostDataSource(), /* page size */ size
        ).build()
    }

    fun getRandomPosts(limit: Int): LiveData<List<Post>>? {
        return try {
            sInstance?.mIoExecutor?.submit<LiveData<List<Post>>> { mDao.getRandom(limit) }?.get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            null
        } catch (e: ExecutionException) {
            e.printStackTrace()
            null
        }

    }

    fun getAllPosts(): LiveData<List<Post>>? {
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

    fun insert(post: Post) {
        mIoExecutor.submit { mDao.insert(post) }
    }

    val count: LiveData<Int>?
        get() {
            return try {
                sInstance?.mIoExecutor?.submit(mDao::getRawCount)?.get()
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
        private var sInstance: PostDataRepository? = null

        fun getInstance(application: Application): PostDataRepository {
            if (sInstance == null) {
                synchronized(PostDataRepository::class.java) {
                    if (sInstance == null) {
                        val database = PostDatabase.getInstance(application)
                        sInstance = PostDataRepository(
                            database!!.postDao(),
                            Executors.newSingleThreadExecutor()
                        )

                        val executor = Executors.newSingleThreadExecutor()
                        executor.submit(sInstance!!::requestPosts)
                    }
                }
            }
            return sInstance!!
        }
    }
}