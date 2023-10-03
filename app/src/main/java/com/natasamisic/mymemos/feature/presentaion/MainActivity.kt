package com.natasamisic.mymemos.feature.presentaion


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.natasamisic.mymemos.feature.presentaion.add_memo.AddEditMemoScreen
import com.natasamisic.mymemos.feature.presentaion.memos.MemosScreen
import com.natasamisic.mymemos.feature.presentaion.util.Screen
import com.natasamisic.mymemos.ui.theme.mymemosTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        mymemosTheme {

            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setStatusBarColor(color = Color.Transparent)
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Gray,
                    contentWindowInsets = WindowInsets.captionBar
                ) {
                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        startDestination = Screen.MemosScreen.route
                    ) {
                        composable(
                            route = Screen.MemosScreen.route
                        ) {
                            MemosScreen(navController = navController)
                        }

                        composable(
                            route = Screen.AddEditMemoScreen.route + "?MemoId={MemoId}&MemoColor={MemoColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "MemoId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "MemoColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },

                                )
                        ) {
                            val color = it.arguments?.getInt("MemoColor") ?: -1
                            AddEditMemoScreen(navController = navController, memoColor = color)
                        }
                    }
                }
            }
        }
    }

}
