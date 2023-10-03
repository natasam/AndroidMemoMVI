package com.natasamisic.mymemos.feature.domain.use_case


import com.natasamisic.mymemos.feature.domain.model.MemoDto
import com.natasamisic.mymemos.feature.domain.repository.MemoRepository
import com.natasamisic.mymemos.feature.domain.util.MemoSortType
import com.natasamisic.mymemos.feature.domain.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMemosUseCase(
    private val repository: MemoRepository
) {

    operator fun invoke(
        memoSortType: MemoSortType = MemoSortType.Date(SortType.Descending)
    ): Flow<List<MemoDto>> {
        return repository.getMemos().map { Memos ->
            when (memoSortType.sortType) {
                is SortType.Ascending -> {
                    when (memoSortType) {
                        is MemoSortType.Title -> Memos.sortedBy { it.title.lowercase() }
                        is MemoSortType.Date -> Memos.sortedBy { it.timestamp }
                        is MemoSortType.Color -> Memos.sortedBy { it.colorPriority }
                    }
                }
                is SortType.Descending -> {
                    when (memoSortType) {
                        is MemoSortType.Title -> Memos.sortedByDescending { it.title.lowercase() }
                        is MemoSortType.Date -> Memos.sortedByDescending { it.timestamp }
                        is MemoSortType.Color -> Memos.sortedByDescending { it.colorPriority }
                    }
                }
            }
        }
    }

}