package com.example.newswithcleanarchitecture

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.Constants.SEARCHNEWS_FIELD
import com.example.newswithcleanarchitecture.bottom_navigation.BottomNavItem
import com.example.newswithcleanarchitecture.bottom_navigation.components.BottomNavigationBar
import com.example.newswithcleanarchitecture.bottom_navigation.components.Navigation
import com.example.newswithcleanarchitecture.ui.theme.NewsWithCleanArchitectureTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NewsEndtoEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalMaterialApi::class)
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
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

    @Test
    fun searchNewsTest() {

        composeRule.onNodeWithContentDescription("검색").performClick()

        composeRule
            .onNodeWithContentDescription(SEARCHNEWS_FIELD)
            .performTextInput("경기")

        composeRule.onNodeWithText("경기").assertIsDisplayed()








    }





}