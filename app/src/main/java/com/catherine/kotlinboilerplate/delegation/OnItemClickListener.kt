package com.catherine.kotlinboilerplate.delegation

import android.view.View

interface OnItemClickListener {
    fun onClick(view: View, pos: Int)
}