package com.example.newswithcleanarchitecture.favorite_news

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newswithcleanarchitecture.components.ArticlePrevItem
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteNewsScreen(
    navController: NavController,
    viewModel: FavoriteNewsViewModel = hiltViewModel()
) {

    val articlesState = viewModel.state.value

    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState

    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize()
        ) {
            items(articlesState) { article ->
                val swipeableState = rememberSwipeableState(initialValue = 0)


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .swipeable(
                            state = swipeableState,
                            anchors = mapOf(
                                0f to 0,
                                -dipToPx(context, 100f) to 1
                            ),
                            thresholds = { _, _ ->
                                FractionalThreshold(0.3f)
                            },
                            orientation = androidx.compose.foundation.gestures.Orientation.Horizontal
                        )
                ) {
                    // show delete when left swipe
                    IconButton(
                        onClick = {

                            scope.launch {
                                swipeableState.animateTo(0,tween(10,0))
                                viewModel.onEvent(NewsEvent.DeleteArticle(article))
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "기사가 삭제되었습니다",
                                    actionLabel = "취소"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NewsEvent.RestoreArticle)
                                }
                            }

                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 30.dp)
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier
                                .size(40.dp)
                        )

                    }

                    ArticlePrevItem(
                        modifier = Modifier
                            .offset {
                                IntOffset(swipeableState.offset.value.roundToInt(), 0)
                            }
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                navController.navigate(
                                    "article" +
                                            "?articleId=${article.id}&publishedAt=${article.publishedAt}" +
                                            "&sourceId=${article.source.id}&sourceName=${article.source.name}" +
                                            "&title=${article.title}&url=${article.url}&urlToImage=${article.urlToImage}"
                                )
                            },
                        article = article,
                    )
                }
                Divider()
            }
        }


    }





}
private fun dipToPx(context: Context, dipValue: Float): Float {
    return dipValue * context.resources.displayMetrics.density
}