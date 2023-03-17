package com.example.newswithcleanarchitecture.bottom_navigation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newswithcleanarchitecture.breaking_news.BreakingNewsScreen
import com.example.newswithcleanarchitecture.favorite_news.FavoriteNewsScreen
import com.example.newswithcleanarchitecture.search_news.SearchNewsScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "breakingnews") {
        composable("breakingnews") {
            BreakingNewsScreen()
        }
        composable("favoritenews") {
            FavoriteNewsScreen()
        }
        composable("searchnews") {
            SearchNewsScreen()
        }
    }
}