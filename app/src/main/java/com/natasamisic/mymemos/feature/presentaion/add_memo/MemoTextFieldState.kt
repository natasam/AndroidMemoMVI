package com.natasamisic.mymemos.feature.presentaion.add_memo

data class MemoTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)