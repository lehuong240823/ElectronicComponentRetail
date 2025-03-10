package org.example.project.ui.components


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import org.example.project.ui.theme.ButtonColor
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography

@Composable
fun BodyText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = Typography.Style.BodyText,
    color: ButtonColor = Themes.Light.primaryLayout,
) {
    Text(
        modifier = modifier,
        style = textStyle.copy(color = color.primaryText!!),
        text = text,
    )
}