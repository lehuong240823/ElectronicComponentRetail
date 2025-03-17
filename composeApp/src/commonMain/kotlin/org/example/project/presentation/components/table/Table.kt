package org.example.project.presentation.components.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import kotlinx.datetime.format
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.Divider
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes

@Composable
fun Table(
    headers: List<String>,
    weights: List<Float>,
    textAligns: List<TextAlign>? = emptyList(),
    color: ButtonColor = Themes.Light.primaryLayout,
    tableHeader: @Composable () -> Unit = {
        TableHeader(
            headers = headers,
            weights = weights,
            textAligns = textAligns
        )
    },
    tableRowsContent: @Composable ColumnScope.() -> Unit
) {
    Form(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(color = color.defaultBackground!!, shape = RectangleShape),
            verticalArrangement = Arrangement.spacedBy(Size.Padding.Xl)
        ) {
            tableHeader()
            Divider()
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Size.Padding.Xl)
            ) {
                tableRowsContent()
            }
        }
    }
}