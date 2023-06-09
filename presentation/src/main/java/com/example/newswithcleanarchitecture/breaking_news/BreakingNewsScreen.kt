package com.example.newswithcleanarchitecture.breaking_news

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newswithcleanarchitecture.components.ArticlePrevItem
import com.example.newswithcleanarchitecture.components.NewsListingFromApiSection

@Composable
fun BreakingNewsScreen(
    navController: NavController,
    viewModel: BreakingNewsViewModel = hiltViewModel()
) {

    NewsListingFromApiSection(navController = navController, viewModel = viewModel)
}
