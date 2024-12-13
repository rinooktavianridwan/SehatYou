package com.example.sehatyou.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sehatyou.model.DiaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diary: DiaryEntity)

    @Update
    suspend fun update(diary: DiaryEntity)

    @Delete
    suspend fun delete(diary: DiaryEntity)

    @Query("SELECT * FROM diary_table")
    fun getAll(): Flow<List<DiaryEntity>>

    @Query("SELECT * FROM diary_table WHERE id = :id LIMIT 1")
    suspend fun getDiaryById(id: Int): DiaryEntity?

    @Query("SELECT * FROM diary_table WHERE title LIKE '%' || :searchText || '%' OR description LIKE '%' || :searchText || '%'")
    fun searchDiaries(searchText: String): Flow<List<DiaryEntity>>

    @Query("SELECT * FROM diary_table ORDER BY date DESC, time DESC LIMIT :limit")
    suspend fun getLatestDiaries(limit: Int): List<DiaryEntity>
}
