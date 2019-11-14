package com.catherine.kotlinboilerplate.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.catherine.kotlinboilerplate.data.Album
import com.catherine.kotlinboilerplate.data.AlbumDataRepository


class ShowAlbumViewModel(
    private var mRepository: AlbumDataRepository
) : ViewModel() {

    private var mLiveData: LiveData<PagedList<Album>>
    var albumCount: LiveData<Int>?
    val artistCount: LiveData<Int>?

    init {
        mLiveData = mRepository.getAlbums(PAGE_SIZE)
        albumCount = mRepository.getAlbumCount()
        artistCount = mRepository.getArtistCount()
    }

    fun loadAll(): LiveData<PagedList<Album>> {
        return mLiveData
    }

    companion object {
        const val PAGE_SIZE = 10
    }

}