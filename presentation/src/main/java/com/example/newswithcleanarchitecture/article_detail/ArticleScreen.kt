package com.example.newswithcleanarchitecture.article_detail

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.domain.model.Article

@Composable
fun ArticleScreen(article: Article) {
    
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