package org.example.project.presentation.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

@Composable
fun Tag(
    text: String,
    icon: ImageVector? = null,
    contentPadding: PaddingValues = PaddingValues(Size.Space.S200),
    color: org.example.project.presentation.theme.ButtonColor = Themes.Light.primaryBrandTag,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = color.defaultBackground!!,
                    shape = RoundedCornerShape(Size.Radius.R200)
                )
                .padding(contentPadding),
            horizontalArrangement = horizontalArrangement,
        ) {
            BodyText(
                text = text,
                style = Typography.Style.ButtonText.merge(
                    color = color.primaryText!!,
                )
            )
        }
    }

}