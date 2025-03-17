package org.example.project.presentation.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.CustomRoundedCorner
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

@Composable
fun InputField(
    modifier: Modifier = Modifier.wrapContentSize(),
    minWidth: Dp = 100.dp,
    maxWidth: Dp = 350.dp,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    label: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    textAlign: TextAlign = TextAlign.Left,
    enabled: Boolean = true,
    shape: Shape = CustomRoundedCorner(),
    colors: ButtonColor = Themes.Light.textField
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Size.Space.S200),
    ) {
        if (label != null) {
            BodyText(
                text = label,
                style = Typography.Style.Label.copy(color = colors.primaryText!!)
            )
        }
        CustomBasicTextField(
            modifier = modifier,
            minWidth = minWidth,
            maxWidth = maxWidth,
            value = value,
            onValueChange = onValueChange,
            trailingIcon = trailingIcon,
            textAlign = textAlign,
            shape = shape,
            padding = padding,
            enabled = enabled,
            colors = colors
        )
    }
}

@Composable
fun CustomBasicTextField(
    modifier: Modifier = Modifier.wrapContentSize(),
    value: String,
    onValueChange: (String) -> Unit,
    minWidth: Dp = 100.dp,
    maxWidth: Dp = 350.dp,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    border: Shape = CustomRoundedCorner(),
    trailingIcon: @Composable (() -> Unit)? = null,
    textAlign: TextAlign = TextAlign.Left,
    enabled: Boolean = true,
    shape: Shape = CustomRoundedCorner(),
    colors: ButtonColor = Themes.Light.textField
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = modifier
            .border(
                width = Size.Stroke.Border,
                color = if (isFocused) colors.primaryText!! else colors.border!!,
                shape = shape
            )
            .background(colors.defaultBackground!!)
            .widthIn(min = minWidth, max = maxWidth),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {
        BasicTextField(
            value = TextFieldValue(text = value, selection = TextRange(value.length)),
            onValueChange = { onValueChange(it.text) },
            textStyle = Typography.Style.InputField.copy(
                color = colors.primaryText!!,
                textAlign = textAlign
                ),
            singleLine = true,
            enabled = enabled,
            cursorBrush = SolidColor(colors.primaryText!!),
            modifier = Modifier.weight(1f)
                .padding(padding)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
        )
        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}

