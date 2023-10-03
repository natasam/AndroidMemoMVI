package com.natasamisic.mymemos.feature.ui


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.natasamisic.mymemos.feature.ui.add_memo.AddMemoScreen
import com.natasamisic.mymemos.feature.ui.memos.MemosScreen
import com.natasamisic.mymemos.feature.ui.util.Screen
import com.natasamisic.mymemos.ui.theme.MyMemosTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent { MyAppContent() }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyAppContent() {
    MyMemosTheme {
        SetUpStatusBar()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.Gray
            ) {
                SetUpNavigationHost(navController)
            }
        }
    }
}

@Composable
fun SetUpStatusBar() {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Transparent)
    }
}

@Composable
fun SetUpNavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MemosScreen.route
    ) {
        composable(route = Screen.MemosScreen.route) {
            MemosScreen(navController = navController)
        }
        composable(
            route = Screen.AddEditMemoScreen.route + "?memoId={memoId}&color={color}",
            arguments = listOf(
                navArgument(name = "memoId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(name = "color") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val color = backStackEntry.arguments?.getInt("color") ?: -1
            AddMemoScreen(navController = navController, memoColor = color, onNavigateToHomeScreen = { navController.navigate(Screen.MemosScreen.route) })
        }
    }
}
