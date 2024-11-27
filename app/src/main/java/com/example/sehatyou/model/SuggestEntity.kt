package com.example.sehatyou.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suggest_table")
data class SuggestEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var descripsion: String,
    var favorite: Boolean,
    var date: String,
    var time: String,
)