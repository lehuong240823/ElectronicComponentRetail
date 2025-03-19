package org.example.project.presentation.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import electroniccomponentretail.composeapp.generated.resources.Image
import electroniccomponentretail.composeapp.generated.resources.Res
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Typography
import org.jetbrains.compose.resources.painterResource

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier.wrapContentSize(),
) {
    Form(
        modifier = modifier,
        padding = Size.Space.S200,
        space = Size.Space.S200,
        shape = RectangleShape,
        minWidth = 80.dp,
        maxWidth = 115.dp,
    ) {

        Image(
            modifier = Modifier.aspectRatio(1f),
            painter = painterResource(Res.drawable.Image),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
        BodyText(
            text = "Category",
        )
    }
}