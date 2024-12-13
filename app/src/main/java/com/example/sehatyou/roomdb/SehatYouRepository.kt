package com.example.sehatyou.roomdb

import com.example.sehatyou.model.DiaryEntity
import com.example.sehatyou.model.SuggestEntity
import kotlinx.coroutines.flow.Flow

interface SehatYouRepository {

    fun getAll(): Flow<List<SuggestEntity>>

    suspend fun insert(toDoList: SuggestEntity)

    suspend fun update(toDoList: SuggestEntity)

    suspend fun delete(toDoList: SuggestEntity)

    // Diary functions
    fun getAllDiaries(): Flow<List<DiaryEntity>>
    suspend fun insertDiary(diary: DiaryEntity)
    suspend fun updateDiary(diary: DiaryEntity)
    suspend fun deleteDiary(diary: DiaryEntity)
    suspend fun getDiaryById(id: Int): DiaryEntity?
    fun searchDiaries(searchText: String): Flow<List<DiaryEntity>>

    suspend fun getLatestDiaries(limit: Int): List<DiaryEntity>
    suspend fun getLatestSuggestions(limit: Int): List<SuggestEntity>

}