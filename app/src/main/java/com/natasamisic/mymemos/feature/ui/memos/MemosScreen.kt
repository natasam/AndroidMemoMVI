package com.natasamisic.mymemos.feature.ui.memos

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.natasamisic.mymemos.R
import com.natasamisic.mymemos.feature.ui.memos.componants.MemoItem
import com.natasamisic.mymemos.feature.ui.memos.componants.SortingView
import com.natasamisic.mymemos.feature.ui.util.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MemosScreen(
    navController: NavController,
    //onSettingsClick: () -> Unit,
    viewModel: MemosViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier
                        .weight(5f, true)
                        .padding(top = 50.dp, start = 26.dp),
                    text = stringResource(R.string.my_memos),
                    style = MaterialTheme.typography.headlineMedium, color = Color.Black
                )
            }
        },
        bottomBar = {
            MemosBottomBar(onSettingsClick = {
                showBottomSheet = true
                viewModel.onEvent(MemosEvent.ToggleOrderSection)
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEditMemoScreen.route) },
                containerColor = Color.Black

            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "add Memo",
                    tint = Color.White
                )
            }
        },
        backgroundColor = Color(0xFFF4F4EF),
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(horizontal = 16.dp, vertical = 36.dp)
        ) {
            if (showBottomSheet) {
                ModalBottomSheet(
                    dragHandle = {
                        OutlinedButton(

                            onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }) {
                            Text(text = "Close",  style = MaterialTheme.typography.bodyLarge, )
                        }
                    },
                    scrimColor = Color.Transparent,
                    onDismissRequest = {
                        showBottomSheet = false

                    },
                    sheetState = sheetState,
                    containerColor = Color.White
                ) {
                    SortingView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        memoSortType = state.memoOrder,
                        onOrderChange = {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                            viewModel.onEvent(MemosEvent.Sort(it))
                        }
                    )
                }
            }
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 6.dp,

                horizontalArrangement = Arrangement.spacedBy(6.dp),
                content = {
                    this.items(state.memos) { memo ->
                        MemoItem(
                            memo = memo,
                            onItemClick = {
                                viewModel.onEvent(MemosEvent.EditMemo(memo))
                            },
                            onDeleteClick = {
                                viewModel.onEvent(MemosEvent.DeleteMemo(memo))
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Memo deleted!",
                                        actionLabel = "Undo"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(MemosEvent.RestoreMemo)
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}

@Composable
fun MemosBottomBar(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = Color(0xFFE8C0BD).copy(alpha = 0.65f),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings button",
                    tint = Color.Black
                )
            }
        }
    }
}