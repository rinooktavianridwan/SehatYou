package com.example.sehatyou.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sehatyou.model.DiaryEntity
import com.example.sehatyou.model.SuggestEntity

@Database(entities = [SuggestEntity::class, DiaryEntity::class], version = 1)
abstract class SehatYouDatabase : RoomDatabase() {
    abstract fun suggestDao(): SuggestDao
    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile
        private var INSTANCE: SehatYouDatabase? = null

        fun getDatabase(context: Context): SehatYouDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SehatYouDatabase::class.java,
                    "sehatyou_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
