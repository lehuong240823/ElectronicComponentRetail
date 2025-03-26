package org.example.project.presentation.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import electroniccomponentretail.composeapp.generated.resources.Image
import electroniccomponentretail.composeapp.generated.resources.Res
import org.example.project.domain.model.Category
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Typography
import org.jetbrains.compose.resources.painterResource

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier.wrapContentSize(),
    category: Category = Category()
) {
    Form(
        modifier = modifier,
        padding = Size.Space.S200,
        space = Size.Space.S200,
        shape = RectangleShape,
        minWidth = 80.dp,
        maxWidth = 115.dp,
    ) {
        AsyncImage(
            modifier = Modifier.aspectRatio(1f),
            contentScale = ContentScale.FillWidth,
            model = category.image,
            error = painterResource(Res.drawable.Image),
            placeholder = painterResource(Res.drawable.Image),
            contentDescription = null,
        )
        BodyText(
            text = category.name?:"",
        )
    }
}