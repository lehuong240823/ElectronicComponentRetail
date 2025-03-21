package org.example.project.presentation.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
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
    placeHolder: String? = null,
    textAlign: TextAlign = TextAlign.Left,
    enabled: Boolean = true,
    shape: Shape = CustomRoundedCorner(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    colors: ButtonColor = Themes.Light.textField
) {
    Column(
        modifier = modifier.wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(Size.Space.S200),
    ) {
        if (label != null) {
            BodyText(
                text = label,
                //style = Typography.Style.Label.merge(color = colors.primaryText!!)
            )
        }
        CustomBasicTextField(
            modifier = Modifier.fillMaxWidth(),
            minWidth = minWidth,
            maxWidth = maxWidth,
            value = value,
            onValueChange = onValueChange,
            trailingIcon = trailingIcon,
            placeHolder = placeHolder,
            textAlign = textAlign,
            shape = shape,
            padding = padding,
            enabled = enabled,
            visualTransformation = visualTransformation,
            singleLine = singleLine,
            maxLines = maxLines,
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
    placeHolder: String? = null,
    textAlign: TextAlign = TextAlign.Left,
    enabled: Boolean = true,
    shape: Shape = CustomRoundedCorner(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
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
            modifier = Modifier.weight(1f)
                .padding(padding)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            value = value,//TextFieldValue(text = value, selection = TextRange.Zero),
            onValueChange = onValueChange,//onValueChange(it.text) },
            textStyle = Typography.Style.InputField.copy(
                color = colors.primaryText!!,
                textAlign = textAlign
            ),
            singleLine = singleLine,
            maxLines = maxLines,
            enabled = enabled,
            cursorBrush = SolidColor(colors.primaryText!!),
            visualTransformation = visualTransformation,
            decorationBox = { innerTextField ->
                if (!isFocused && value.isNullOrEmpty() && !placeHolder.isNullOrEmpty()) {
                    BodyText(
                        text = placeHolder,
                        style = Typography.Style.InputField.copy(color = colors.tertiaryText?: colors.border?: Color.Gray)
                    )
                } else {
                    innerTextField()
                }
            },
        )
        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}

