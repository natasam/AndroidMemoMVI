package com.natasamisic.mymemos.presentaion.add_edit_Memo

data class MemoTextFeildState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)