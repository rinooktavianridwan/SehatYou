package com.example.sehatyou.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sehatyou.model.SuggestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SuggestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(Suggest: SuggestEntity)

    @Update
    suspend fun update(suggestion: SuggestEntity)

    @Delete
    suspend fun delete(Suggest: SuggestEntity)

    @Query("SELECT * FROM suggest_table")
    fun getAll(): Flow<List<SuggestEntity>>

    @Query("SELECT * FROM suggest_table ORDER BY date DESC, time DESC LIMIT :limit")
    suspend fun getLatestSuggestions(limit: Int): List<SuggestEntity>
}