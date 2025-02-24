package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    colors: ButtonColor = Themes.Light.textField
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(Size.Space.S200),
    ) {
        Text(
            text = label,
            style = Typography.Style.Label.copy(color = colors.text!!)
        )
        CustomBasicTextField(value, onValueChange, colors)
    }
}

@Composable
fun CustomBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    colors: ButtonColor = Themes.Light.textField
) {
    var isFocused = remember { mutableStateOf(false).value }

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (isFocused) colors.text!! else colors.border!!,
                shape = RoundedCornerShape(Size.Radius.R200)
            )
            .background(colors.background!!)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused

            },
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = Typography.Style.InputField.copy(color = colors.text!!),
            singleLine = true,
            cursorBrush = SolidColor(colors.text!!),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
