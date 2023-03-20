package com.example.newswithcleanarchitecture.bottom_navigation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.domain.model.Article
import com.example.domain.model.Source
import com.example.newswithcleanarchitecture.article_detail.ArticleScreen
import com.example.newswithcleanarchitecture.breaking_news.BreakingNewsScreen
import com.example.newswithcleanarchitecture.favorite_news.FavoriteNewsScreen
import com.example.newswithcleanarchitecture.search_news.SearchNewsScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "breakingnews") {
        composable("breakingnews") {
            BreakingNewsScreen(navController)
        }
        composable("favoritenews") {
            FavoriteNewsScreen(navController)
        }
        composable("searchnews") {
            SearchNewsScreen(navController)
        }
        composable(
            route = "article" +
                    "?articleId={articleId}&publishedAt={publishedAt}&sourceId={sourceId}&sourceName={sourceName}"+
                    "&title={title}&url={url}&urlToImage={urlToImage}",
            arguments = listOf(
                navArgument(
                    name = "articleId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "publishedAt"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(
                    name = "sourceId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "sourceName"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(
                    name = "title"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(
                    name = "url"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(
                    name = "urlToImage"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            ArticleScreen(
                Article(
                    id = it.arguments?.getInt("articleId") ?: -1,
                    publishedAt = it.arguments?.getString("publishedAt") ?: "",
                    source = Source(
                        id = it.arguments?.getInt("sourceId") ?: -1,
                        name = it.arguments?.getString("sourceName") ?: ""
                    ),
                    title = it.arguments?.getString("title") ?: "",
                    url = it.arguments?.getString("url") ?: "",
                    urlToImage = it.arguments?.getString("urlToImage") ?: ""
                )
            )
        }
    }
}