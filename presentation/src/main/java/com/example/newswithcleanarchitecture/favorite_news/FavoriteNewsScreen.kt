package com.example.newswithcleanarchitecture.favorite_news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newswithcleanarchitecture.components.ArticlePrevItem

@Composable
fun FavoriteNewsScreen(
    navController: NavController,
    viewModel: FavoriteNewsViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state) { article ->
                ArticlePrevItem(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                "article" +
                                        "?articleId=${article.id}&publishedAt=${article.publishedAt}"+
                                        "&sourceId=${article.source.id}&sourceName=${article.source.name}"+
                                        "&title=${article.title}&url=${article.url}&urlToImage=${article.urlToImage}"
                            )
                        },
                    article = article,
                )
                Divider()
            }
        }
    }
}