package com.natasamisic.mymemos.feature.ui.memos.componants

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.natasamisic.mymemos.R
import com.natasamisic.mymemos.feature.domain.util.MemoSortType
import com.natasamisic.mymemos.feature.domain.util.SortType


@Composable
fun SortingView(
    modifier: Modifier = Modifier,
    memoSortType: MemoSortType = MemoSortType.Date(SortType.Descending),
    onOrderChange: (MemoSortType) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SortByOption(
                optionText = stringResource(R.string.title),
                isSelected = memoSortType is MemoSortType.Title,
                onSelect = { onOrderChange(MemoSortType.Title(memoSortType.sortType)) }
            )

            SortByOption(
                optionText = stringResource(R.string.date),
                isSelected = memoSortType is MemoSortType.Date,
                onSelect = { onOrderChange(MemoSortType.Date(memoSortType.sortType)) }
            )

            SortByOption(
                optionText = stringResource(R.string.color),
                isSelected = memoSortType is MemoSortType.Color,
                onSelect = { onOrderChange(MemoSortType.Color(memoSortType.sortType)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            SortByOrder(
                orderText = stringResource(R.string.ascending),
                isSelected = memoSortType.sortType is SortType.Ascending,
                onSelect = { onOrderChange(memoSortType.copy(SortType.Ascending)) }
            )

            SortByOrder(
                orderText = stringResource(R.string.descending),
                isSelected = memoSortType.sortType is SortType.Descending,
                onSelect = { onOrderChange(memoSortType.copy(SortType.Descending)) }
            )
        }
    }
}

@Composable
private fun SortByOption(
    optionText: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    DefaultRadioButton(
        text = optionText,
        selected = isSelected,
        onSelect = onSelect
    )
    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
private fun SortByOrder(
    orderText: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    DefaultRadioButton(
        text = orderText,
        selected = isSelected,
        onSelect = onSelect
    )
    Spacer(modifier = Modifier.width(8.dp))
}
