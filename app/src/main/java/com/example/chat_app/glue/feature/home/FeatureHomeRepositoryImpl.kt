package com.example.chat_app.glue.feature.home

import com.example.home.FeatureHomeRepository
import com.example.utils.helper.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class FeatureHomeRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : FeatureHomeRepository {

    override fun getChannels(): Task<DataSnapshot> {
        return firebaseDatabase.getReference(Constants.REFERENCE_CHANNEL).get()
    }

    override fun addNewChannel(name: String): Task<Void> {
        val key = firebaseDatabase.getReference(Constants.REFERENCE_CHANNEL).push().key
        return firebaseDatabase.getReference(Constants.REFERENCE_CHANNEL)
            .child(key!!)
            .setValue(name)
    }
}