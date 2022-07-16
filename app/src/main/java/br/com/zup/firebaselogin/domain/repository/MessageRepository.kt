package br.com.zup.firebaselogin.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase

class MessageRepository {
    private val authentication: FirebaseAuth = Firebase.auth
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference("firebaseapplogin/${authentication.currentUser?.uid}/UserSavedMessages")

    fun databaseRef() = reference

    fun getSavedMessagesList(): Query{
        return reference.orderByValue()
    }
}