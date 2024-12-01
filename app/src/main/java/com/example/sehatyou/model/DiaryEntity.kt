package com.example.sehatyou.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary_table")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var description: String,
    var category: String,
    var favorite: Boolean = false,
    var date: String,
    var time: String
)

