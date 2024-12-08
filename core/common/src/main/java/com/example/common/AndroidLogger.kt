package com.example.common

import android.util.Log

class AndroidLogger {

    fun log(message: String) {
        Log.d("AndroidLogger", message)
    }

    fun err(exception: Throwable, message: String? = null) {
        Log.e("AndroidLogger", message, exception)
    }

}