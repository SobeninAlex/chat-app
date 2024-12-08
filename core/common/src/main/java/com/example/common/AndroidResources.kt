package com.example.common

import android.content.Context

class AndroidResources(
    private val applicationContext: Context,
) {

    fun getString(id: Int): String {
        return applicationContext.getString(id)
    }

    fun getString(id: Int, vararg placeholders: Any): String {
        return applicationContext.getString(id, placeholders)
    }

}