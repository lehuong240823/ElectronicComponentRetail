package org.example.project.ui.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TableCell(
    borderColor: Color = Color.Black,
    text: String,
    fontColor: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    Box(
        modifier = Modifier.border(0.5.dp, borderColor).fillMaxHeight().padding(8.dp).weight(1f)
    ) {
        Text(
            color = fontColor,
            fontWeight = fontWeight,
            text = text
        )
    }
}


@Composable
fun RowScope.TableCell(
    borderColor: Color = Color.Black,
    content: @Composable () -> Unit,
) {
    Box(modifier = Modifier.border(0.5.dp, borderColor).fillMaxHeight().padding(8.dp).weight(1f)) {
        content()
    }
}