package com.catherine.kotlinboilerplate.paging

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.catherine.kotlinboilerplate.data.AlbumDataRepository
import com.catherine.kotlinboilerplate.data.PostDataRepository
import java.lang.reflect.InvocationTargetException


class PostWallViewModelFactory() : ViewModelProvider.Factory {

    private lateinit var mRepository: PostDataRepository

    private constructor(repository: PostDataRepository) : this() {
        this.mRepository = repository
    }

    fun createFactory(activity: Activity): PostWallViewModelFactory {
        val application = activity.application
            ?: throw IllegalStateException("Not yet attached to Application")
        return PostWallViewModelFactory(PostDataRepository.getInstance(application))
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            return modelClass.getConstructor(PostDataRepository::class.java)
                .newInstance(mRepository)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }

    }

}