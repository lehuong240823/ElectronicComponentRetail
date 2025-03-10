package org.example.project.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography

@Composable
fun TextButton(
    text: String,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier.wrapContentSize(),
    isMenuItem: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    color: org.example.project.ui.theme.ButtonColor = Themes.Light.primaryButton,
    contentPadding: PaddingValues = PaddingValues(Size.Space.S200),
    textVisibility: Boolean = true,
    onClick: () -> Unit
) {
    Box {
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        Button(
            modifier = modifier.hoverable(interactionSource),
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(backgroundColor = if (isHovered) color.hoverBackground!! else color.defaultBackground!!),
            border = BorderStroke(Size.Stroke.Border, color.border!!),
            shape = CustomRoundedCorner(),
            contentPadding = contentPadding,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp
            ),
            onClick = onClick
        ) {
            Row(
                modifier = if (isMenuItem && textVisibility) Modifier.fillMaxWidth().wrapContentHeight() else Modifier.wrapContentSize(),
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        modifier = Modifier.size(if (isMenuItem) Size.Icon.Small else Size.Icon.Default),
                        contentDescription = null,
                        tint = color.icon!!
                    )
                    if (text != null && textVisibility) {
                        Spacer(Modifier.width(Size.Space.S200))
                    }
                }
                AnimatedVisibility(visible = textVisibility) {
                    Text(
                        text = text,
                        style = Typography.Style.ButtonText.copy(
                            color = color.primaryText!!,
                            //fontWeight = if (isHovered) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
}