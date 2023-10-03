package com.natasamisic.mymemos.feature.presentaion.add_memo

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.natasamisic.mymemos.feature.domain.util.ColorUtils.memoColors
import com.natasamisic.mymemos.presentaion.add_edit_Memo.componants.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditMemoScreen(
    navController: NavController,
    memoColor: Int,
    viewModel: AddEditMemoViewModel = hiltViewModel()
) {

    val titleState = viewModel.memoTitle.value
    val contentState = viewModel.memoContent.value

    val scaffoldState = rememberScaffoldState()

    val memoBackgroundAnimatable = remember {
        Animatable(
            Color(if (memoColor != -1) memoColor else viewModel.memoColor.value)
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditMemoViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }

                is AddEditMemoViewModel.UiEvent.SaveMemo -> {
                    navController.navigateUp()
                }
            }

        }
    }

    Scaffold(
        topBar = {

            Text(
                modifier = Modifier.padding(top = 50.dp, start = 26.dp),
                text = "My Memos",
                style = MaterialTheme.typography.headlineMedium, color = Color.Black

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditMemoEvent.SaveMemo)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "save Memo")
            }
        },
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(memoBackgroundAnimatable.value)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                memoColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.memoColor.value == colorInt) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    memoBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditMemoEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = { viewModel.onEvent(AddEditMemoEvent.EnteredTitle(it)) },
                onFocusChange = {
                    viewModel.onEvent(AddEditMemoEvent.ChangedTitleFocused(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = { viewModel.onEvent(AddEditMemoEvent.EnteredContent(it)) },
                onFocusChange = {
                    viewModel.onEvent(AddEditMemoEvent.ChangedContentFocused(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}