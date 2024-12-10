package com.example.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface FeatureAuthRepository {

    fun signIn(email: String, password: String): Task<AuthResult>

    fun signUp(email: String, password: String): Task<AuthResult>

}