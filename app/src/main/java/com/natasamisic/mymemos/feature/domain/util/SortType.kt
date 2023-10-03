package com.natasamisic.mymemos.feature.domain.util

sealed class SortType{
    object Ascending : SortType()
    object Descending : SortType()
}
