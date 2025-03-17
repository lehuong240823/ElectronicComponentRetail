package org.example.project.presentation.components.common


import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

@Composable
fun BodyText(
    modifier: Modifier = Modifier,
    text: String,
    color: ButtonColor = Themes.Light.primaryLayout,
    style: TextStyle = Typography.Style.BodyText,
    textAlign: TextAlign? = null,
) {
    Text(
        modifier = modifier,
        style = LocalTextStyle.current
            .merge(
                color = color.primaryText!!,
                letterSpacing = 0.sp
            )
            .merge(style),
        text = text,
        textAlign = textAlign
    )
}