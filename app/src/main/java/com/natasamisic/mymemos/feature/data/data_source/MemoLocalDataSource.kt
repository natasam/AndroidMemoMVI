package com.natasamisic.mymemos.feature.data.data_source

import com.natasamisic.mymemos.feature.data.data_source.db.MemosDao
import com.natasamisic.mymemos.feature.data.data_source.db.model.Memo
import javax.inject.Inject

class MemoLocalDataSource @Inject constructor(private val dao: MemosDao) {
    suspend fun insertMemo(memo: Memo) =
        dao.insertMemo(memo)
    fun getMemos() = dao.getMemos()
    suspend fun getMemoById(memoId: Int) =
        dao.getMemoById(memoId)
    suspend fun deleteMemo(memo: Memo) =
        dao.deleteMemo(memo)
}