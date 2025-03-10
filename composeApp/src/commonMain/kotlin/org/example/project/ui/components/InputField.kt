package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import org.example.project.ui.theme.ButtonColor
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography

@Composable
fun InputField(
    modifier: Modifier = Modifier.wrapContentSize(),
    label: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    colors: ButtonColor = Themes.Light.textField
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Size.Space.S200),
    ) {
        if (label != null) {
            Text(
                text = label,
                style = Typography.Style.Label.copy(color = colors.primaryText!!)
            )
        }
        CustomBasicTextField(value, onValueChange, enabled, colors)
    }
}

@Composable
fun CustomBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    colors: ButtonColor = Themes.Light.textField
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .border(
                width = Size.Stroke.Border,
                color = if (isFocused) colors.primaryText!! else colors.border!!,
                shape = CustomRoundedCorner()
            )
            .background(colors.defaultBackground!!)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .widthIn(min = 100.dp, max = 350.dp)

    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = Typography.Style.InputField.copy(color = colors.primaryText!!),
            singleLine = true,
            enabled = enabled,
            cursorBrush = SolidColor(colors.primaryText!!),
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
        )
    }
}
