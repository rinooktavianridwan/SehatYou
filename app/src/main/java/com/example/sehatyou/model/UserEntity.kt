package com.example.sehatyou.model

import androidx.room.PrimaryKey

data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var kontak : String = "",
    var beratBadan: String = "",
    var tinggiBadan: String = "",
    var tglLahir: String = "",
    var jamKerjaStart: String = "",
    var jamKerjaEnd: String = ""
)
