package com.natasamisic.mymemos.feature.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.natasamisic.mymemos.feature.data.data_source.model.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemosDao {

    @Query("SELECT * FROM Memo")
    fun getMemos(): Flow<List<Memo>>

    @Query("SELECT * FROM Memo WHERE id = :id")
    suspend fun getMemoById(id: Int): Memo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(Memo: Memo)

    @Delete
    suspend fun deleteMemo(Memo: Memo)

}