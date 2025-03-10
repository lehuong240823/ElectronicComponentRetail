package org.example.project.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Typography

@Composable
@Preview
fun OrderTable(
    headers: List<String>,
    rows: List<List<String>>,
    weights: List<Float>,
    textAligns: List<TextAlign> = listOf(TextAlign.Left),
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Size.Padding.Xl)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            headers.forEachIndexed { idx, header ->
                Text(
                    text = header,
                    modifier = Modifier
                        .weight(if (!weights.isEmpty() && weights[idx] != null) weights[idx] else 1f),
                    style = Typography.Style.BodyStrong,
                    textAlign = if (!textAligns.isEmpty() && textAligns[idx] != null) textAligns[idx] else TextAlign.Left
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
                        if (idx == 0) {
                            Row(
                                modifier = Modifier
                                    .weight(if (!weights.isEmpty() && weights[idx] != null) weights[idx] else 1f),
                                horizontalArrangement = Arrangement.spacedBy(Size.Space.S200),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    modifier = Modifier.size(36.dp),
                                    imageVector = Icons.Outlined.Image,
                                    contentDescription = null,
                                )
                                Text(
                                    text = cell,
                                    style = Typography.Style.BodyText,
                                    textAlign = if (!textAligns.isEmpty() && textAligns[idx] != null) textAligns[idx] else TextAlign.Left
                                )
                            }
                        } else {
                            Text(
                                text = cell,
                                modifier = Modifier
                                    .weight(if (!weights.isEmpty() && weights[idx] != null) weights[idx] else 1f),
                                style = Typography.Style.BodyText,
                                textAlign = if (!textAligns.isEmpty() && textAligns[idx] != null) textAligns[idx] else TextAlign.Left
                            )
                        }
                    }
                }
                Divider()
            }
        }
    }
}