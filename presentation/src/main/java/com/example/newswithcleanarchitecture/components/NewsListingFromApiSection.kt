package com.example.newswithcleanarchitecture.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newswithcleanarchitecture.NewsFromApiViewModel

@Composable
fun NewsListingFromApiSection(
    navController: NavController,
    viewModel: NewsFromApiViewModel
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.articles.size ) { i ->
                if(i >= state.articles.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadNextItems()
                }
                ArticlePrevItem(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                "article" +
                                        "?articleId=${state.articles[i].id}&publishedAt=${state.articles[i].publishedAt}"+
                                        "&sourceId=${state.articles[i].source.id}&sourceName=${state.articles[i].source.name}"+
                                        "&title=${state.articles[i].title}&url=${state.articles[i].url}&urlToImage=${state.articles[i].urlToImage}"
                            )
                        },
                    article = state.articles[i],
                )
                Divider()
            }
            item {
                if(state.isLoading && state.articles.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
        state.error?.let {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading && state.articles.isEmpty()) {

            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        }
    }

}