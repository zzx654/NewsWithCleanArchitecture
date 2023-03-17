package com.example.newswithcleanarchitecture.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import coil.compose.AsyncImage
import com.example.domain.model.Article

@Composable
fun ArticlePrevItem(
    modifier: Modifier = Modifier,
    article: Article
) {

    val constraints = ConstraintSet {
        val NewsImage = createRefFor("NewsImage")
        val NewsPrevCol = createRefFor("NewsPrevCol")
        val guideLine = createGuidelineFromStart(0.7f)
        constrain(NewsImage) {
            top.linkTo(parent.top)
            start.linkTo(guideLine)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }
        constrain(NewsPrevCol) {
            top.linkTo(NewsImage.top)
            bottom.linkTo(NewsImage.bottom)
            start.linkTo(parent.start)
            end.linkTo(guideLine,25.dp)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
    }
    ConstraintLayout(constraints, modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        AsyncImage(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(20))
                .layoutId("NewsImage"),
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(Color.Gray),
            error = ColorPainter(Color.Gray),

            model = article.urlToImage,
            contentDescription = null,
        )

        Column(
            modifier = Modifier
                .layoutId("NewsPrevCol"),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                fontWeight = FontWeight.Bold,
                text = article.title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.publishedAt,
                color = Color.Gray
            )

        }
    }
}