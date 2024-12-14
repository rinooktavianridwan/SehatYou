package com.example.sehatyou.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.sehatyou.model.UserEntity
import com.google.firebase.database.FirebaseDatabase

// Fungsi untuk menyimpan data ke Firebase
fun saveUserDataToFirebase(
    userId: String?,
    userEntity: UserEntity,
    context: Context
) {
    if (userId != null) {
        val databaseUrl = "https://sehatyou-88c86-default-rtdb.asia-southeast1.firebasedatabase.app/"
        val database = FirebaseDatabase.getInstance(databaseUrl).getReference("users").child(userId)

        database.setValue(userEntity)
            .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context, "Gagal menyimpan data: ${exception.message}", Toast.LENGTH_SHORT).show()
            Log.e("saveUserDataToFirebase", "Failure: ${exception.message}")
        }
    } else {
        Toast.makeText(context, "User ID tidak ditemukan!", Toast.LENGTH_SHORT).show()
    }
}

// Fungsi untuk mengambil data dari Firebase
fun getUserDataFromFirebase(
    userId: String?,
    context: Context,
    onDataRetrieved: (UserEntity) -> Unit
) {
    if (userId != null) {
        val database = FirebaseDatabase.getInstance().getReference("users").child(userId)

        database.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val userEntity = snapshot.getValue(UserEntity::class.java)
                if (userEntity != null) {
                    onDataRetrieved(userEntity)
                } else {
                    Toast.makeText(context, "Data tidak ditemukan!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Data tidak ditemukan!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context, "Gagal mengambil data: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "User ID tidak ditemukan!", Toast.LENGTH_SHORT).show()
    }
}
