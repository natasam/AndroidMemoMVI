package com.natasamisic.mymemos.feature.data.mapper

import com.natasamisic.mymemos.feature.data.data_source.db.model.Memo
import com.natasamisic.mymemos.feature.domain.model.MemoDto

object MemoMapper {

    fun List<Memo>.mapFromDbToMemoEntityList(): List<MemoDto> {
        return this.map {
            MemoDto(
                id = it.id,
                colorPriority = it.colorPriority,
                text = it.text,
                timestamp = it.timestamp,
                title = it.title
            )
        }
    }

    fun Memo.mapFromDbToMemoEntity(): MemoDto {
        return MemoDto(
                id = id,
                colorPriority = colorPriority,
                text = text,
                timestamp = timestamp,
                title = title
            )
        }
    fun MemoDto.mapFromEntityToMemo(): Memo {
        return Memo(
            id = id,
            colorPriority = colorPriority,
            text = text,
            timestamp = timestamp,
            title = title
        )
    }

}