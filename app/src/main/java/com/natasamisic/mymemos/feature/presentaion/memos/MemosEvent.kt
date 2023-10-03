package com.natasamisic.mymemos.feature.presentaion.memos

import com.natasamisic.mymemos.feature.domain.model.MemoDto
import com.natasamisic.mymemos.feature.domain.util.MemoSortType


sealed class MemosEvent {
    data class Sort(val order: MemoSortType) : MemosEvent()
    data class DeleteMemo(val memo: MemoDto) : MemosEvent()
    data class EditMemo(val memo: MemoDto) : MemosEvent()
    object RestoreMemo : MemosEvent()
    object ToggleOrderSection : MemosEvent()
}
