package com.catherine.kotlinboilerplate.paging

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.catherine.kotlinboilerplate.data.AlbumDataRepository
import java.lang.reflect.InvocationTargetException


class ShowAlbumViewModelFactory() : ViewModelProvider.Factory {

    private lateinit var mRepository: AlbumDataRepository

    private constructor(repository: AlbumDataRepository) : this() {
        this.mRepository = repository
    }

    fun createFactory(activity: Activity): ShowAlbumViewModelFactory {
        val application = activity.application
            ?: throw IllegalStateException("Not yet attached to Application")
        return ShowAlbumViewModelFactory(AlbumDataRepository.getInstance(application))
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            return modelClass.getConstructor(AlbumDataRepository::class.java)
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