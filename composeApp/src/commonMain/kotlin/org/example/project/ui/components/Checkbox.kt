package org.example.project.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import org.example.project.ui.theme.Typography

@Composable
fun Checkbox(
    text: String,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = false, onCheckedChange = onCheckedChange)
        Text(
            text = text,
            style = Typography.Style.Caption
        )
    }
}