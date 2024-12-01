package com.example.sehatyou.roomdb

import com.example.sehatyou.model.DiaryEntity
import com.example.sehatyou.model.SuggestEntity
import kotlinx.coroutines.flow.Flow

class OfflineSehatYouRepository(
    private val suggestDao: SuggestDao,
    private val diaryDao: DiaryDao
) : SehatYouRepository {
    override fun getAll(): Flow<List<SuggestEntity>> = suggestDao.getAll()
    override suspend fun insert(suggestEntity: SuggestEntity) = suggestDao.insert(suggestEntity)
    override suspend fun update(suggestEntity: SuggestEntity) = suggestDao.update(suggestEntity)
    override suspend fun delete(suggestEntity: SuggestEntity) = suggestDao.delete(suggestEntity)

    // Diary functions
    override fun getAllDiaries(): Flow<List<DiaryEntity>> = diaryDao.getAll()
    override suspend fun insertDiary(diary: DiaryEntity) = diaryDao.insert(diary)
    override suspend fun updateDiary(diary: DiaryEntity) = diaryDao.update(diary)
    override suspend fun deleteDiary(diary: DiaryEntity) = diaryDao.delete(diary)
    override suspend fun getDiaryById(id: Int): DiaryEntity? = diaryDao.getDiaryById(id)
    override fun searchDiaries(searchText: String): Flow<List<DiaryEntity>> =
        diaryDao.searchDiaries(searchText)

}