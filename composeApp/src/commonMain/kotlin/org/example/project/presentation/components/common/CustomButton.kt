package org.example.project.presentation.components.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

@Composable
fun CustomButton(
    modifier: Modifier = Modifier.wrapContentSize(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    contentPadding: PaddingValues = PaddingValues(Size.Space.S300),
    onClick: () -> Unit,
    text: String,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    isFillMaxWidth: Boolean = false,
    isIconFirst: Boolean = false,
    textVisibility: Boolean = true,
    shape: Shape = CustomRoundedCorner(),
    color: ButtonColor = Themes.Light.primaryButton,
) {
    val style = LocalTextStyle.current.fontFamily

    MaterialTheme(
        typography = androidx.compose.material.Typography(
            defaultFontFamily = style?: FontFamily.Monospace,
            button = MaterialTheme.typography.button.merge(
                letterSpacing = 0.sp,
                fontFamily = style?: FontFamily.Monospace),
            )
    ){
        Row(
            modifier = modifier
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsHoveredAsState()
            Button(
                modifier = modifier.hoverable(interactionSource),
                interactionSource = interactionSource,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isHovered) color.hoverBackground!! else color.defaultBackground!!,
                    disabledBackgroundColor = color.disabledBackground ?: color.defaultBackground!!.copy(alpha = 0.12f)
                ),
                border = BorderStroke(
                    Size.Stroke.Border,
                    color.border ?: Color.Transparent),
                enabled = enabled,
                shape = shape,
                contentPadding = contentPadding,
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    hoveredElevation = 0.dp,
                    focusedElevation = 0.dp
                ),
                onClick = onClick
            ) {
                Row(
                    modifier = if (isFillMaxWidth && textVisibility) Modifier.fillMaxWidth().wrapContentHeight() else Modifier.wrapContentSize(),
                    horizontalArrangement = horizontalArrangement,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(isIconFirst) {
                        if (icon != null) {
                            CustomButtonIcon(
                                icon = icon,
                                isMenuItem = isFillMaxWidth,
                                isHovered = isHovered,
                                enabled = enabled,
                                color = color
                            )
                            if (text != null && textVisibility) {
                                Spacer(Modifier.width(Size.Space.S200))
                            }
                        }
                        CustomButtonText(
                            text = text,
                            textVisibility = textVisibility,
                            isHovered = isHovered,
                            enabled = enabled,
                            color = color
                        )
                    } else {
                        if (text != null && textVisibility) {
                            CustomButtonText(
                                text = text,
                                textVisibility = textVisibility,
                                isHovered = isHovered,
                                enabled = enabled,
                                color = color
                            )
                            if (icon != null) {
                                Spacer(Modifier.width(Size.Space.S200))
                                CustomButtonIcon(
                                    icon = icon,
                                    isMenuItem = isFillMaxWidth,
                                    isHovered = isHovered,
                                    enabled = enabled,
                                    color = color
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun CustomButtonText(
    text: String,
    textVisibility: Boolean = true,
    isHovered: Boolean,
    enabled: Boolean,
    color: ButtonColor,
) {
    AnimatedVisibility(visible = textVisibility) {
        BodyText(
            text = text,
            style = Typography.Style.ButtonText.merge(
                color = if(!enabled && color.disabledPrimaryText != null) color.disabledPrimaryText
                else if(isHovered && color.hoverPrimaryText != null) color.hoverPrimaryText
                else if(enabled && color.primaryText != null) color.primaryText else Color.Black,
            )
        )
    }
}

@Composable
fun CustomButtonIcon(
    icon: ImageVector,
    isMenuItem: Boolean = false,
    isHovered: Boolean,
    enabled: Boolean,
    color: ButtonColor,
) {
    Icon(
        imageVector = icon,
        modifier = Modifier.size(if (isMenuItem) Size.Icon.Small else Size.Icon.Default),
        contentDescription = null,
        tint = color.icon
            ?: if(!enabled) color.disabledPrimaryText!! else if(isHovered && color.hoverPrimaryText != null) color.hoverPrimaryText else color.primaryText!!,
    )
}