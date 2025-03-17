package org.example.project.presentation.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun RoundIconButton(
    res: DrawableResource,
    onClick: () -> Unit = {},
    colors: ButtonColor = Themes.Light.textField,
) {
    IconButton(
        modifier = Modifier.size(36.dp).border(Size.Stroke.Border, colors.border!!, shape = CircleShape),
        onClick = onClick,
    ) {
        Image(painter = painterResource(res), contentDescription = null)
    }
}

