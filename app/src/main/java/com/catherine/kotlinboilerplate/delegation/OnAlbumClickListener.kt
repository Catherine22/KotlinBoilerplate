package com.catherine.kotlinboilerplate.delegation

import android.view.View
import com.catherine.kotlinboilerplate.data.Album

interface OnAlbumClickListener {
    fun onClick(view: View, album: Album?)
}