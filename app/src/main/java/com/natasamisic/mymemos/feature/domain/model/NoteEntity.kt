package com.natasamisic.mymemos.feature.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemoEntity(
    val title: String,
    val text: String,
    val timestamp: Long,
    val colorPriority: Int,
    @PrimaryKey val id: Int? = null
)



class InvalidMemoException(message: String): Exception(message)