package com.example.newswithcleanarchitecture.search_news

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.data.Constants.SEARCHNEWS_FIELD
import com.example.newswithcleanarchitecture.components.NewsListingFromApiSection

@Composable
fun SearchNewsScreen(
    navController: NavController,
    viewModel: SearchNewsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Column(
        modifier = Modifier.fillMaxSize()

    ) {
        OutlinedTextField(

            value = state.value.searchQuery,
            onValueChange = {
                viewModel.searchQueryChange(it)
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .semantics {
                    contentDescription = SEARCHNEWS_FIELD

                },
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true,

        )
        NewsListingFromApiSection(navController = navController, viewModel = viewModel)

    }
}