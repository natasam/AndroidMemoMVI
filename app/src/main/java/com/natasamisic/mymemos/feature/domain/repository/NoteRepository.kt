package com.natasamisic.mymemos.feature.domain.repository

import com.natasamisic.mymemos.feature.domain.model.MemoDto
import kotlinx.coroutines.flow.Flow

interface MemoRepository {

    fun getMemos(): Flow<List<MemoDto>>

    suspend fun getMemoById(id: Int): MemoDto?

    suspend fun insertMemo(memo: MemoDto)

    suspend fun deleteMemo(memo: MemoDto)

}