package com.example.chat_app.glue.feature.auth

import com.example.auth.FeatureAuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FeatureAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FeatureAuthRepository {

    override fun signIn(email: String, password: String) : Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    override fun signUp(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }
}