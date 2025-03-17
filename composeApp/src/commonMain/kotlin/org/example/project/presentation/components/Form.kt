package org.example.project.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.common.CustomRoundedCorner
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes

@Composable
fun Form(
    modifier: Modifier = Modifier.wrapContentSize(),
    padding: Dp = Size.Space.S600,
    space: Dp = Size.Space.S600,
    minWidth: Dp = Dp.Unspecified,
    maxWidth: Dp = 320.dp,
    horizontalArrangement: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(space),
    color: ButtonColor = Themes.Light.primaryLayout,
    shape: Shape = CustomRoundedCorner(),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.clip(shape = shape)
            .background(color = color.defaultBackground!!)
            .border(width = Size.Stroke.Border, color = color.border!!, shape = shape)
            .widthIn(min = minWidth, max = /*if (maxWidth != Dp.Unspecified)*/ maxWidth /*else minWidth*/)
            .padding(padding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalArrangement
    ) {
        content()
    }
}