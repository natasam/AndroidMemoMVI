package com.natasamisic.mymemos.feature.presentaion.add_memo

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import com.natasamisic.mymemos.feature.presentaion.add_memo.componants.CustomHintTextField
import com.natasamisic.mymemos.feature.presentaion.util.NeubrutalismHelper.applyBrutalism
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddMemoScreen(
    navController: NavController,
    memoColor: Int,
    viewModel: AddEditMemoViewModel = hiltViewModel(), onNavigateToHomeScreen: () -> Unit
) {
    val titleState = viewModel.memoTitle.value
    val contentState = viewModel.memoContent.value

    val scaffoldState = rememberScaffoldState()

    val memoBackgroundAnimatable = remember {
        Animatable(Color(if (memoColor != -1) memoColor else viewModel.memoColor.value))
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
        backgroundColor = memoBackgroundAnimatable.value,
        topBar = {
            TopAppBar(elevation = 0.dp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 38.dp),
                backgroundColor = memoBackgroundAnimatable.value,
                title = { MemoTopBar() },
                navigationIcon = { BackButton(onNavigateToHomeScreen) }
            )
        },
        floatingActionButton = { MemoFAB { viewModel.onEvent(AddEditMemoEvent.SaveMemo) } },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .fillMaxHeight()
                .background(memoBackgroundAnimatable.value)

        ) {
            PriorityPickerRow(
                selectedColor = viewModel.memoColor.value,
                onColorSelected = { color ->
                    scope.launch {
                        memoBackgroundAnimatable.animateTo(
                            targetValue = Color(color),
                            animationSpec = tween(durationMillis = 500)
                        )
                    }
                    viewModel.onEvent(AddEditMemoEvent.ChangeColor(color))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomHintTextField(
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
            CustomHintTextField(
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

@Composable
private fun BackButton(onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(
            modifier = Modifier
                .size(48.dp)
                .applyBrutalism(
                    backgroundColor = Color.White,
                    borderWidth = 3.dp,
                    cornersRadius = 40.dp
                )
                .padding(4.dp),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black
        )
    }
}
@Composable
fun MemoTopBar() {
    Text(
        modifier = Modifier.padding(top = 2.dp, start = 26.dp),
        text = "New memo",
        style = MaterialTheme.typography.headlineMedium,
        color = Color.Black
    )
}

@Composable
fun MemoFAB(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier.padding(bottom = 50.dp),
        onClick = onClick,
        containerColor = Color(0xFFD8ED49)
    ) {
        Icon(imageVector = Icons.Default.Check, contentDescription = "save Memo")
    }
}

@Composable
fun PriorityPickerRow(
    selectedColor: Int,
    onColorSelected: (Int) -> Unit
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
                    .applyBrutalism(
                        backgroundColor = color,
                        cornersRadius = 40.dp, shadowColor =
                        if (selectedColor == colorInt) Color.Black else Color.Gray,
                        offsetY = if (selectedColor == colorInt) 0.dp else 1.dp,
                        offsetX = if (selectedColor == colorInt) 0.dp else 1.dp
                    )
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { onColorSelected(colorInt) }
            )
        }
    }
}
