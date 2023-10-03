package com.natasamisic.mymemos.feature.domain.repository

import com.natasamisic.mymemos.feature.data.data_source.model.Memo
import kotlinx.coroutines.flow.Flow

interface MemoRepository {

    fun getMemos(): Flow<List<Memo>>

    suspend fun getMemoById(id: Int): Memo?

    suspend fun insertMemo(Memo: Memo)

    suspend fun deleteMemo(Memo: Memo)

}