package com.natasamisic.mymemos.feature.domain.usecase

import com.natasamisic.mymemos.feature.domain.model.MemoDto
import com.natasamisic.mymemos.feature.domain.repository.MemoRepository


class GetMemoUseCase(
    private val repository: MemoRepository
) {
    suspend operator fun invoke(id: Int): MemoDto? {
        return repository.getMemoById(id)
    }
}