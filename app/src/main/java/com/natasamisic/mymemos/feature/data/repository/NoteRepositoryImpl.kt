package com.natasamisic.mymemos.feature.data.repository

import com.natasamisic.mymemos.feature.data.data_source.MemosDao
import com.natasamisic.mymemos.feature.data.data_source.model.Memo
import com.natasamisic.mymemos.feature.domain.repository.MemoRepository
import kotlinx.coroutines.flow.Flow

class MemoRepositoryImpl(
    private val memosDao: MemosDao
) : MemoRepository {

    override fun getMemos(): Flow<List<Memo>> {
        return memosDao.getMemos()
    }

    override suspend fun getMemoById(id: Int): Memo {
        return memosDao.getMemoById(id)!!
    }

    override suspend fun insertMemo(Memo: Memo) {
        memosDao.insertMemo(Memo)
    }

    override suspend fun deleteMemo(Memo: Memo) {
        memosDao.deleteMemo(Memo)
    }

}