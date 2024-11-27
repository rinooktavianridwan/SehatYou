package com.example.sehatyou.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sehatyou.model.SuggestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SuggestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(Suggest: SuggestEntity)

    @Delete
    suspend fun delete(Suggest: SuggestEntity)

    @Query("SELECT * FROM suggest_table")
    fun getAll(): Flow<List<SuggestEntity>>
}