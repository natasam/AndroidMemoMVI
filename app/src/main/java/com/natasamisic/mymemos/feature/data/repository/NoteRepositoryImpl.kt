package com.natasamisic.mymemos.feature.data.repository

import com.natasamisic.mymemos.feature.data.data_source.MemoLocalDataSource
import com.natasamisic.mymemos.feature.data.mapper.MemoMapper.mapFromDbToMemoEntity
import com.natasamisic.mymemos.feature.data.mapper.MemoMapper.mapFromDbToMemoEntityList
import com.natasamisic.mymemos.feature.data.mapper.MemoMapper.mapFromEntityToMemo
import com.natasamisic.mymemos.feature.domain.model.MemoDto
import com.natasamisic.mymemos.feature.domain.repository.MemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val memosDataSource: MemoLocalDataSource
) : MemoRepository {

    override fun getMemos(): Flow<List<MemoDto>> {
        return memosDataSource.getMemos().map {
            it.mapFromDbToMemoEntityList()
        }
    }

    override suspend fun getMemoById(id: Int): MemoDto {
        return memosDataSource.getMemoById(id)!!.mapFromDbToMemoEntity()
    }

    override suspend fun insertMemo(memo: MemoDto) {
        memosDataSource.insertMemo(memo.mapFromEntityToMemo())
    }

    override suspend fun deleteMemo(memo: MemoDto) {
        memosDataSource.deleteMemo(memo.mapFromEntityToMemo())
    }

}