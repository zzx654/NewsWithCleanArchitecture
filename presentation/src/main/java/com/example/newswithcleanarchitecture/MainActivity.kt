package com.example.newswithcleanarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity




import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newswithcleanarchitecture.bottom_navigation.BottomNavItem
import com.example.newswithcleanarchitecture.bottom_navigation.components.BottomNavigationBar
import com.example.newswithcleanarchitecture.bottom_navigation.components.Navigation
import com.example.newswithcleanarchitecture.ui.theme.NewsWithCleanArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsWithCleanArchitectureTheme {
                val navController = rememberNavController()
                var isBottomBarVisible = true
                val navBackStackEntryState = navController.currentBackStackEntryAsState()
                navBackStackEntryState.value?.destination?.route?.let { route ->
                    isBottomBarVisible = when(route){
                        "breakingnews" -> true
                        "searchnews" -> true
                        "favoritenews" -> true
                        else -> false
                    }
                }
                Scaffold(
                    bottomBar = {
                        if(isBottomBarVisible)
                        {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "속보",
                                        route = "breakingnews",
                                        icon = Icons.Default.Warning
                                    ),
                                    BottomNavItem(
                                        name = "즐겨찾기",
                                        route = "favoritenews",
                                        icon = Icons.Default.Favorite
                                    ),
                                    BottomNavItem(
                                        name = "검색",
                                        route = "searchnews",
                                        icon = Icons.Default.Search
                                    ),
                                ),
                                navController = navController,
                                onItemClick = {

                                    navController.navigate(it.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // 같은아이템 재선택시 같은 desination 생성 방지
                                        launchSingleTop = true
                                        //아이템 선택시 state 복구
                                        restoreState = true
                                    }
                                }
                            )
                        }

                    }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        Navigation(navController = navController)
                    }
                }
            }
        }
    }
}

