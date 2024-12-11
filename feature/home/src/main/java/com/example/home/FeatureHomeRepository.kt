package com.example.home

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

interface FeatureHomeRepository {

    fun getChannels(): Task<DataSnapshot>

    fun addNewChannel(name: String): Task<Void>

}