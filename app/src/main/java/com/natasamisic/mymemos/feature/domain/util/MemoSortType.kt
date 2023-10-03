package com.natasamisic.mymemos.feature.domain.util

sealed class MemoSortType(val sortType: SortType) {
    class Title(sortType: SortType) : MemoSortType(sortType)
    class Date(sortType: SortType) : MemoSortType(sortType)
    class Color(sortType: SortType) : MemoSortType(sortType)

    fun copy(sortType: SortType): MemoSortType {
        return when (this) {
            is Title -> Title(sortType)
            is Date -> Date(sortType)
            is Color -> Color(sortType)
        }
    }
}
