package com.natasamisic.mymemos.feature.domain.use_case

import com.natasamisic.mymemos.feature.data.data_source.model.Memo
import com.natasamisic.mymemos.feature.domain.repository.MemoRepository


class DeleteMemoUseCase(
    private val repository: MemoRepository
) {

    suspend operator fun invoke(Memo: Memo) {
        repository.deleteMemo(Memo)
    }

}