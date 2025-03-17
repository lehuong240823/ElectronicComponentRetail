package org.example.project.presentation.components.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CustomIconToggleButton(
    modifier: Modifier = Modifier,
    checked: MutableState<Boolean> = mutableStateOf(true),
    onValueChange: (Boolean) -> Unit,
    text: String,
    trueIcon: DrawableResource,
    falseIcon: DrawableResource,
    contentPadding: PaddingValues = PaddingValues(Size.Space.S200),
    color: ButtonColor = Themes.Light.toggleButton,
) {
    val style = LocalTextStyle.current.fontFamily
    MaterialTheme(
        typography = androidx.compose.material.Typography(defaultFontFamily = style!!, button = MaterialTheme.typography.button.merge(letterSpacing = 0.sp))
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        Button(
            modifier = modifier
                .clip(shape = CustomRoundedCorner())
                .padding(Size.Space.S200)
                .toggleable(
                    value = checked.value,
                    onValueChange = onValueChange,
                    enabled = true,
                    role = Role.Switch,
                    interactionSource = interactionSource,
                    indication = null
                ),
            interactionSource = interactionSource,
            contentPadding = contentPadding,
            shape = CustomRoundedCorner(),
            border = BorderStroke(
                Size.Stroke.Border,
                if (checked.value) color.defaultBackground!! else color.hoverBackground!!
            ),
            colors = ButtonDefaults.buttonColors(backgroundColor = if (checked.value) color.defaultBackground!! else color.hoverBackground!!),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp
            ),
            onClick = { checked.value = !checked.value },
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Size.Space.S200)
            ) {
                if (checked.value) {
                    Icon(
                        painter = painterResource(trueIcon),
                        contentDescription = null,
                        tint = if (checked.value) color.primaryText!! else color.secondaryText!!
                    )
                } else {
                    Icon(
                        painter = painterResource(falseIcon),
                        contentDescription = null,
                        tint = if (checked.value) color.primaryText!! else color.secondaryText!!
                    )
                }
                if (!text.isNullOrEmpty()) {
                    Text(
                        text = text,
                        style = Typography.Style.ButtonText.copy(
                            color = if (checked.value) color.primaryText!! else color.secondaryText!!,
                        )
                    )
                }
            }

        }
    }
}