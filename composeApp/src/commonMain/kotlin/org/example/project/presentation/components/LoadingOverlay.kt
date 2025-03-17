package org.example.project.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Themes

@Composable
fun LoadingOverlay(
    color: ButtonColor = Themes.Light.primaryLayout
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                color = (color.defaultBackground ?: Color.White).copy(alpha = 0.3F)
            )
            .clickable(
                enabled = false,
                onClickLabel = null,
                role = null,
                onClick = {}
            ),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = color.primaryText ?: Color.Black
        )
    }
}