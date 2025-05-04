package com.elevate

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService {
    private val db = FirebaseFirestore.getInstance()

    // Add a document
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

    // Get a document
    fun getUser(userId: String, function: () -> Unit) {
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Firestore", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting document", exception)
            }
    }

    // Update a document
    fun updateUser(userId: String, newEmail: String) {
        val user = hashMapOf("userEmail" to newEmail)

        db.collection("users").document(userId)
            .update(user)
            .addOnSuccessListener {
                Log.d("Firestore", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error updating document", e)
            }
    }

    // Delete a document
    fun deleteUser(userId: String) {
        db.collection("users").document(userId)
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error deleting document", e)
            }
    }
}