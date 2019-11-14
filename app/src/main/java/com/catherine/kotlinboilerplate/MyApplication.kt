package com.catherine.kotlinboilerplate

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(this)
    }
}