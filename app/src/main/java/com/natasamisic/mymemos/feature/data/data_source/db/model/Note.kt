package com.natasamisic.mymemos.feature.data.data_source.db.model

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
