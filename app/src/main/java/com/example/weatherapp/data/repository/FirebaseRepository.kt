package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.FavoriteCity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.util.Log

class FirebaseRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: DatabaseReference =
        FirebaseDatabase.getInstance().reference


    private fun userId(): String =
        auth.currentUser?.uid ?: ""

    fun signIn(onComplete: () -> Unit) {
        if (auth.currentUser != null) {
            onComplete()
            return
        }

        auth.signInAnonymously()
            .addOnSuccessListener { onComplete() }
    }

    fun observeFavorites(
        onChange: (List<FavoriteCity>) -> Unit
    ) {
        val ref = db.child("favorites").child(userId())

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val list = mutableListOf<FavoriteCity>()

                for (child in snapshot.children) {
                    child.getValue(FavoriteCity::class.java)
                        ?.let { list.add(it) }
                }

                onChange(list)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun addFavorite(
        city: String,
        note: String
    ) {
        val id = db.push().key ?: return

        val favorite = FavoriteCity(
            id = id,
            city = city,
            note = note,
            createdAt = System.currentTimeMillis(),
            createdBy = userId()
        )

        db.child("favorites")
            .child(userId())
            .child(id)
            .setValue(favorite)
    }

    fun updateNote(
        id: String,
        note: String
    ) {
        db.child("favorites")
            .child(userId())
            .child(id)
            .child("note")
            .setValue(note)
    }

    fun deleteFavorite(
        id: String
    ) {
        db.child("favorites")
            .child(userId())
            .child(id)
            .removeValue()
    }

    init {
        FirebaseAuth.getInstance().signInAnonymously()
            .addOnSuccessListener { result ->
                Log.d("FIREBASE_AUTH", "Auth OK: ${result.user?.uid}")
            }
            .addOnFailureListener { error ->
                Log.e("FIREBASE_AUTH", "Auth FAIL: ${error.message}")
            }
    }
}
