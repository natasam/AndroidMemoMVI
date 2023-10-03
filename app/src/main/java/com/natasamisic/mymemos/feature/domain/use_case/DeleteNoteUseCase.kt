package com.natasamisic.mymemos.feature.domain.use_case

import com.natasamisic.mymemos.feature.domain.model.MemoDto
import com.natasamisic.mymemos.feature.domain.repository.MemoRepository


class DeleteMemoUseCase(
    private val repository: MemoRepository
) {

    suspend operator fun invoke(memo: MemoDto) {
        repository.deleteMemo(memo)
    }

}