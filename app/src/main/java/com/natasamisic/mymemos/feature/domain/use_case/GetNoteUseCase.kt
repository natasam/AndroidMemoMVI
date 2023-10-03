package com.natasamisic.mymemos.feature.domain.use_case

import com.natasamisic.mymemos.feature.data.data_source.model.Memo
import com.natasamisic.mymemos.feature.domain.repository.MemoRepository


class GetMemoUseCase(
    private val repository: MemoRepository
) {
    suspend operator fun invoke(id: Int): Memo? {
        return repository.getMemoById(id)
    }
}