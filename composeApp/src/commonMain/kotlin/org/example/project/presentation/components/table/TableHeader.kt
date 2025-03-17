package org.example.project.presentation.components.table

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.Checkbox
import org.example.project.presentation.theme.Typography

@Composable
fun TableHeader(
    headers: List<String>,
    weights: List<Float>,
    textAligns: List<TextAlign>? = emptyList(),
    showSelectAllCheckBox: Boolean = false,
    selectAllChecked: MutableState<Boolean> = remember { mutableStateOf(false) },
    onselectAllCheckedChange: (Boolean) -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showSelectAllCheckBox) {
            if (onselectAllCheckedChange != null) {
                Checkbox(
                    checked = selectAllChecked,
                    onCheckedChange = onselectAllCheckedChange
                )
            }
        }
        headers.forEachIndexed { idx, header ->
            BodyText(
                text = header,
                modifier = Modifier
                    .weight(if (!weights.isNullOrEmpty()) weights[idx] else 1f),
                style = Typography.Style.BodyStrong,
                textAlign = if (!textAligns.isNullOrEmpty()) textAligns[idx] else TextAlign.Left
            )
        }
    }
}