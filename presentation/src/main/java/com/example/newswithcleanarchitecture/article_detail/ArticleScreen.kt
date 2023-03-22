package com.example.newswithcleanarchitecture.article_detail

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.Article
import com.example.newswithcleanarchitecture.ui.theme.BottomUnSelected
import com.example.newswithcleanarchitecture.ui.theme.FabColor
import com.example.newswithcleanarchitecture.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleScreen(
    article: Article,
    viewModel: ArticleViewModel = hiltViewModel()
) {


    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context,event.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.toggleSave(article)
                },
                backgroundColor = FabColor
            ) {
                val state = viewModel.state.value
                if(state) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "saved", tint = Color.White)
                } else {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "unsaved", tint = Color.White)
                }
            }
        }
    ) {

        AndroidView(factory = {

            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                webViewClient = WebViewClient()
                loadUrl(article.url)
            }
        }, update = {
            it.loadUrl(article.url)
        })

    }


}