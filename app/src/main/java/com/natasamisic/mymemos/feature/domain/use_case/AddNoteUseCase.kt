package com.natasamisic.mymemos.feature.domain.use_case

import com.natasamisic.mymemos.feature.data.data_source.model.InvalidMemoException
import com.natasamisic.mymemos.feature.data.data_source.model.Memo
import com.natasamisic.mymemos.feature.domain.repository.MemoRepository


class AddMemoUseCase(
    private val repository: MemoRepository
) {

    @Throws(InvalidMemoException::class)
    suspend operator fun invoke(Memo: Memo) {
        if (Memo.title.isBlank()) {
            throw InvalidMemoException("The Title of the Memo can't be empty!")
        }
        else if (Memo.text.isBlank()) {
            throw InvalidMemoException("The Content of the Memo can't be empty!")
        }
        repository.insertMemo(Memo)
    }
}