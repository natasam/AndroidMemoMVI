package com.natasamisic.mymemos.feature.presentaion.memos

import com.natasamisic.mymemos.feature.domain.util.MemoSortType
import com.natasamisic.mymemos.feature.domain.util.SortType
import com.natasamisic.mymemos.feature.domain.model.MemoDto

data class MemoState(
    val memos: List<MemoDto> = emptyList(),
    val memoOrder: MemoSortType = MemoSortType.Date(SortType.Descending),
    val isOrderSectionVisible: Boolean = false
)
