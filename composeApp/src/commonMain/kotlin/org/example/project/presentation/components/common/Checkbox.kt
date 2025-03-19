package org.example.project.presentation.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

@Composable
fun Checkbox(
    modifier: Modifier = Modifier,
    text: String? = null,
    checked: MutableState<Boolean> = remember { mutableStateOf(false) },
    onCheckedChange: (Boolean) -> Unit = {},
    color: ButtonColor = Themes.Light.primaryLayout,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = { checked.value = it },
            colors = CheckboxDefaults.colors(checkedColor = color.primaryText?: Color.Black)
        )
        if (!text.isNullOrEmpty())
        {
            BodyText(
                text = text,
                style = Typography.Style.Caption
            )
        }
    }
}