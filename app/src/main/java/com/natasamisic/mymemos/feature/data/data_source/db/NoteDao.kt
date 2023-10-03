package com.natasamisic.mymemos.feature.data.data_source.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.natasamisic.mymemos.feature.data.data_source.db.model.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemosDao {
    @Query("SELECT * FROM Memo")
    fun getMemos(): Flow<List<Memo>>

    @Query("SELECT * FROM Memo WHERE id = :id")
    suspend fun getMemoById(id: Int): Memo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: Memo)

    @Delete
    suspend fun deleteMemo(memo: Memo)

}