package com.catherine.kotlinboilerplate.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.catherine.kotlinboilerplate.data.Post
import com.catherine.kotlinboilerplate.data.PostDataRepository


class PostWallViewModel(
    private var mRepository: PostDataRepository
) : ViewModel() {

    private var mLiveData: LiveData<PagedList<Post>>
    var count: LiveData<Int>?


    init {
        mLiveData = mRepository.getPosts(PAGE_SIZE)
        count = mRepository.count
    }

    fun loadAll(): LiveData<PagedList<Post>> {
        return mLiveData
    }

    companion object {
        const val PAGE_SIZE = 10
    }

}