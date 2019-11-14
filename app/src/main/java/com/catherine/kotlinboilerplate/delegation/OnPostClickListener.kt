package com.catherine.kotlinboilerplate.delegation

import android.view.View
import com.catherine.kotlinboilerplate.data.Album
import com.catherine.kotlinboilerplate.data.Post

interface OnPostClickListener {
    fun onClick(view: View, post: Post?)
}