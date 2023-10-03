package com.natasamisic.mymemos.feature.presentaion.notes

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.material.rememberScaffoldState

import androidx.compose.ui.graphics.Color
import com.natasamisic.mymemos.feature.presentaion.notes.componants.MemoItem
import com.natasamisic.mymemos.feature.presentaion.notes.componants.OrderSection
import com.natasamisic.mymemos.feature.presentaion.util.Screen



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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Text(modifier = Modifier.padding(top = 50.dp, start = 26.dp),
                text = "My Memos",
                style = MaterialTheme.typography.headlineMedium, color = Color.Black
            )
        },
        bottomBar = { MemosBottomBar(onSettingsClick = { viewModel.onEvent(MemosEvent.ToggleOrderSection) }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEditMemoScreen.route) },
                containerColor = Color.Black//Color(0xFFC68CFB)

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


        // Box(
        // modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(colors), alpha = 0.4f
        // )
        //)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(horizontal = 16.dp, vertical = 36.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    memoSortType = state.memoOrder,
                    onOrderChange = {
                        viewModel.onEvent(MemosEvent.Order(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 4.dp,

                horizontalArrangement = Arrangement.spacedBy(4.dp),
                content = {
                    this.items(state.memos){ memo ->
                        MemoItem(
                            Memo = memo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = {}),
                            onDeleteClick = {
                                viewModel.onEvent(MemosEvent.DeleteMemo(memo))
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "memo deleted!",
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
        color = Color(0xFFE8C0BD).copy(alpha = 0.15f),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
        verticalAlignment =  Alignment.Top,
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