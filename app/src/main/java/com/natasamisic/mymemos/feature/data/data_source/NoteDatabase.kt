package com.natasamisic.mymemos.feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.natasamisic.mymemos.feature.data.data_source.model.Memo


@Database(
    entities = [Memo::class],
    version = 1
)
abstract class MemoDatabase: RoomDatabase() {

    abstract val MemosDao: MemosDao

    companion object {
        const val DATABASE_NAME = "Memos_table"
    }
}