package com.natasamisic.mymemos.feature.data.data_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo(
    val title: String,
    val text: String,
    val timestamp: Long,
    val colorPriority: Int,
    @PrimaryKey val id: Int? = null
)

class InvalidMemoException(message: String): Exception(message)