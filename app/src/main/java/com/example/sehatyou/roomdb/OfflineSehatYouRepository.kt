package com.example.sehatyou.roomdb

import com.example.sehatyou.model.SuggestEntity
import kotlinx.coroutines.flow.Flow

class OfflineSehatYouRepository(private val suggestDao: SuggestDao) : SehatYouRepository {
    override fun getAll(): Flow<List<SuggestEntity>> = suggestDao.getAll()

    override suspend fun insert(suggestEntity: SuggestEntity) = suggestDao.insert(suggestEntity)

    override suspend fun update(suggestEntity: SuggestEntity) = suggestDao.update(suggestEntity)

    override suspend fun delete(suggestEntity: SuggestEntity) = suggestDao.delete(suggestEntity)
}