package com.natasamisic.mymemos.feature.presentaion.notes

import com.natasamisic.mymemos.feature.domain.util.MemoSortType
import com.natasamisic.mymemos.feature.domain.util.SortType
import com.natasamisic.mymemos.feature.data.data_source.model.Memo

data class MemoState(
    val memos: List<Memo> = emptyList(),
    val memoOrder: MemoSortType = MemoSortType.Date(SortType.Descending),
    val isOrderSectionVisible: Boolean = false
)
