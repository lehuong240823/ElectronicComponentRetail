package org.example.project.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: org.example.project.ui.theme.ButtonColor = Themes.Light.primaryButton
) {
    Box(modifier = modifier.wrapContentSize()) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = color.background!!),
            shape = RoundedCornerShape(Size.Radius.R200),
            onClick = onClick
        ) {
            Text(
                text = text,
                style = Typography.Style.ButtonText.copy(color = color.text!!),
            )
        }
    }

}