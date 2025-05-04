package com.elevate

import android.util.Log
import com.elevate.model.User
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService {
    private val db = FirebaseFirestore.getInstance()

    fun addUser(userId: String, userName: String, userEmail: String) {
        val user = hashMapOf(
            "userId" to userId,
            "userName" to userName,
            "userEmail" to userEmail
        )

        db.collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                Log.d("Firestore", "Document successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error writing document", e)
            }
    }

    fun getUser(userId: String, onResult: (User?) -> Unit) {
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    onResult(user)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting document", exception)
                onResult(null)
            }
    }

    fun updateUser(userId: String, user: User) {
        val updates = mapOf(
            "userName" to user.userName,
            "userEmail" to user.userEmail
        )

        db.collection("users").document(userId)
            .update(updates)
            .addOnSuccessListener {
                Log.d("Firestore", "Document updated!")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error updating document", e)
            }
    }
}
