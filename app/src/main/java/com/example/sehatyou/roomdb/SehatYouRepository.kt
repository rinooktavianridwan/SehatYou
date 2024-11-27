package com.example.sehatyou.roomdb

import com.example.sehatyou.model.SuggestEntity
import kotlinx.coroutines.flow.Flow

interface SehatYouRepository {

    fun getAll(): Flow<List<SuggestEntity>>

    suspend fun insert(toDoList: SuggestEntity)

    suspend fun delete(toDoList: SuggestEntity)
}