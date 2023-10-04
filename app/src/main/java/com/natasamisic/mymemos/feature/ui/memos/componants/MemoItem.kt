package com.natasamisic.mymemos.feature.ui.memos.componants

import android.text.format.DateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.natasamisic.mymemos.R
import com.natasamisic.mymemos.feature.domain.model.MemoDto
import com.natasamisic.mymemos.feature.ui.util.NeubrutalismHelper.applyBrutalism

@Composable
fun MemoItem(
    memo: MemoDto, onDeleteClick: () -> Unit, onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .applyBrutalism(backgroundColor = Color.White)
            .fillMaxWidth()
            .wrapContentHeight().clickable {  onItemClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 4.dp, bottom = 30.dp, end = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier.weight(5f, true),
                    text = memo.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .weight(1f, true).clip(CircleShape)
                        .applyBrutalism(
                            cornersRadius = 10.dp,
                            backgroundColor = Color(memo.colorPriority),
                            shadowColor = Color.LightGray,
                            offsetX = 0.5.dp,
                            offsetY = 0.5.dp
                        )
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = memo.text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .height(30.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = DateFormat.format(stringResource(R.string.date_format), memo.timestamp).toString(),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(
                onClick = onDeleteClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete memo ${memo.title}",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
