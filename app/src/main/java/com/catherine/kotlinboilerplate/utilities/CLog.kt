package com.catherine.kotlinboilerplate.utilities

import android.util.Log
import com.catherine.kotlinboilerplate.BuildConfig
import com.catherine.kotlinboilerplate.MyApplication
import com.catherine.kotlinboilerplate.R

class CLog {
    companion object {
        private var tag = MyApplication.instance.getString(R.string.app_name)

        fun v(message: String) {
            if (BuildConfig.DEBUG)
                Log.v(tag, message)
        }

        fun v(tag: String, message: String) {
            if (BuildConfig.DEBUG)
                Log.v(tag, message)
        }

        fun i(message: String) {
            if (BuildConfig.DEBUG)
                Log.i(tag, message)
        }

        fun i(tag: String, message: String) {
            if (BuildConfig.DEBUG)
                Log.i(tag, message)
        }

        fun d(message: String) {
            if (BuildConfig.DEBUG)
                Log.d(tag, message)
        }

        fun d(tag: String, message: String) {
            if (BuildConfig.DEBUG)
                Log.d(tag, message)
        }

        fun w(message: String) {
            if (BuildConfig.DEBUG)
                Log.w(tag, message)
        }

        fun w(tag: String, message: String) {
            if (BuildConfig.DEBUG)
                Log.w(tag, message)
        }

        fun e(message: String) {
            Log.e(tag, message)
        }

        fun e(tag: String, message: String) {
            Log.e(tag, message)
        }
    }
}