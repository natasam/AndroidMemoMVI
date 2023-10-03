package com.natasamisic.mymemos.feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemoDto(
    val title: String,
    val text: String,
    val timestamp: Long,
    val colorPriority: Int,
    @PrimaryKey val id: Int? = null
)



