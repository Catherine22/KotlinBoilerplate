package com.catherine.kotlinboilerplate.utilities

import android.content.res.Resources

class DeviceInfo {
    companion object {
        var SCREEN_WIDTH = Resources.getSystem().displayMetrics.widthPixels
            private set
    }
}