package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.project.ui.theme.ButtonColor
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes

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
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.clip(shape = CustomRoundedCorner())
            .background(color = color.defaultBackground!!)
            .border(width = Size.Stroke.Border, color = color.border!!, shape = CustomRoundedCorner())
            .widthIn(min = minWidth, max = if (maxWidth != Dp.Unspecified) maxWidth else minWidth)
            .padding(padding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalArrangement
    ) {
        content()
    }
}