package com.natasamisic.mymemos.feature.presentaion.notes

import com.natasamisic.mymemos.feature.data.data_source.model.Memo
import com.natasamisic.mymemos.feature.domain.util.MemoSortType


sealed class MemosEvent {
    data class Order(val order: MemoSortType) : MemosEvent()
    data class DeleteMemo(val memo: Memo) : MemosEvent()
    object RestoreMemo : MemosEvent()
    object ToggleOrderSection : MemosEvent()
}
