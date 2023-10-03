package com.natasamisic.mymemos.feature.data.data_source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.natasamisic.mymemos.feature.data.data_source.db.MemosDao
import com.natasamisic.mymemos.feature.data.data_source.db.model.Memo


@Database(
    entities = [Memo::class],
    version = 1
)
abstract class MemoDatabase: RoomDatabase() {

    abstract val memosDao: MemosDao

    companion object {
        const val DATABASE_NAME = "Memos_table"
    }
}