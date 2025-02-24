package org.example.project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.ui.theme.ButtonColor
import org.example.project.ui.theme.Themes
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun IconButton(
    res: DrawableResource,
    onClick: () -> Unit = {},
    colors: ButtonColor = Themes.Light.textField,
) {
    IconButton(
        modifier = Modifier.size(36.dp).border(1.dp, colors.border!!, shape = CircleShape),
        onClick = onClick,
    ) {
        Image(painter = painterResource(res), contentDescription = null)
    }
}

