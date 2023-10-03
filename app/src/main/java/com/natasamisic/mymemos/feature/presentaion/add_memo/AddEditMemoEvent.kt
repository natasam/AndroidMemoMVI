package com.natasamisic.mymemos.feature.presentaion.add_memo

import androidx.compose.ui.focus.FocusState

sealed class AddEditMemoEvent {
    data class EnteredTitle(val value: String) : AddEditMemoEvent()
    data class ChangedTitleFocused(val focusState: FocusState) : AddEditMemoEvent()

    data class EnteredContent(val value: String) : AddEditMemoEvent()
    data class ChangedContentFocused(val focusState: FocusState) : AddEditMemoEvent()

    data class ChangeColor(val color: Int) : AddEditMemoEvent()
    object SaveMemo : AddEditMemoEvent()
}


