package org.example.project.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.example.project.ui.theme.Themes

@Composable
@Preview
fun Row(
    color: org.example.project.ui.theme.ButtonColor = Themes.Light.textField,
    content: @Composable RowScope.() -> Unit
) {
    MaterialTheme {
        Row() {
            content()
        }
    }
}