package com.natasamisic.mymemos.feature.presentaion.notes.componants

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.natasamisic.mymemos.feature.domain.util.MemoSortType
import com.natasamisic.mymemos.feature.domain.util.SortType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    memoSortType: MemoSortType = MemoSortType.Date(SortType.Descending),
    onOrderChange: (MemoSortType) -> Unit
) {

    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Title",
                selected = memoSortType is MemoSortType.Title,
                onSelect = { onOrderChange(MemoSortType.Title(memoSortType.sortType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Date",
                selected = memoSortType is MemoSortType.Date,
                onSelect = { onOrderChange(MemoSortType.Date(memoSortType.sortType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Color",
                selected = memoSortType is MemoSortType.Color,
                onSelect = { onOrderChange(MemoSortType.Color(memoSortType.sortType)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                selected = memoSortType.sortType is SortType.Ascending,
                onSelect = {
                    onOrderChange(memoSortType.copy(SortType.Ascending))
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Descending",
                selected = memoSortType.sortType is SortType.Descending,
                onSelect = { onOrderChange(memoSortType.copy(SortType.Descending)) }
            )

        }
    }
}