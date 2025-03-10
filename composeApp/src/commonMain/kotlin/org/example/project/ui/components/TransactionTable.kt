package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import org.example.project.ui.theme.ButtonColor
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography

@Composable
fun TransactionTable(
    headers: List<String> = listOf("Transaction ID", "Order ID", "Payment Method", "Amount", "Status", "Transaction Time"),
    rows: List<List<String>> = listOf(),
    weights: List<Float> = listOf(1f, 1f, 2f, 2f, 1f, 2f),
    color: ButtonColor = Themes.Light.primaryLayout,
    textAligns: List<TextAlign> = listOf(TextAlign.Left),
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(color = color.defaultBackground!!, shape = RectangleShape),
        verticalArrangement = Arrangement.spacedBy(Size.Padding.Xl)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            headers.forEachIndexed { idx, header ->
                Text(
                    text = header,
                    modifier = Modifier
                        .weight(if (!weights.isEmpty() && weights[idx] != null) weights[idx] else 1f),
                    style = Typography.Style.BodyStrong,
                    //textAlign = if (!textAligns.isEmpty() && textAligns[idx] != null) textAligns[idx] else TextAlign.Left
                )
            }
        }
        Divider()

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Size.Padding.Xl)
        ) {
            rows.forEach { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    row.forEachIndexed { idx, cell ->

                    }
                }
            }
        }
    }
}