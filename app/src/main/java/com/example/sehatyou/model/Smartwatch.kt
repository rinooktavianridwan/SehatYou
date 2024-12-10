package com.example.sehatyou.model

data class Smartwatch(
    val name: String,
    val address: String,
    var isConnected: Boolean = false,
    var status: String = "Tidak terhubung"
)
