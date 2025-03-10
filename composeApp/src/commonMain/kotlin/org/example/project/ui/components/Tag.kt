package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography

@Composable
fun Tag(
    text: String,
    icon: ImageVector? = null,
    contentPadding: PaddingValues = PaddingValues(Size.Space.S200),
    color: org.example.project.ui.theme.ButtonColor = Themes.Light.primaryButton,
    modifier: Modifier = Modifier.padding(contentPadding)
        .background(color = color.defaultBackground!!, shape = RoundedCornerShape(Size.Radius.R200)),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
) {
    Row(
        modifier = if (icon != null) modifier.fillMaxWidth().wrapContentHeight() else modifier.wrapContentSize(),
        horizontalArrangement = horizontalArrangement,
    ) {
        Text(
            text = text,
            style = Typography.Style.ButtonText.copy(
                color = color.primaryText!!,
                //fontWeight = if (isHovered) FontWeight.Bold else FontWeight.Normal
            )
        )
        if (icon != null) {
            Spacer(Modifier.width(Size.Space.S200))
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color.icon!!
            )
        }
    }
}