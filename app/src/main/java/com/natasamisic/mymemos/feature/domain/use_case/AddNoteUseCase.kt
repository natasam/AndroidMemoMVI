package com.natasamisic.mymemos.feature.domain.use_case

import com.natasamisic.mymemos.feature.domain.model.InvalidMemoException
import com.natasamisic.mymemos.feature.domain.model.MemoDto
import com.natasamisic.mymemos.feature.domain.repository.MemoRepository

class AddMemoUseCase(
    private val repository: MemoRepository
) {
    @Throws(InvalidMemoException::class)
    suspend operator fun invoke(memo: MemoDto) {
        if (memo.title.isBlank()) {
            throw InvalidMemoException("The Title of the Memo can't be empty!")
        } else if (memo.text.isBlank()) {
            throw InvalidMemoException("The Content of the Memo can't be empty!")
        }
        repository.insertMemo(memo)
    }
}