package org.example.project.presentation.components.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_kid_star
import electroniccomponentretail.composeapp.generated.resources.ic_kid_star_filled
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Themes
import org.jetbrains.compose.resources.painterResource

@Composable
fun RatingToggleGroup(
    modifier: Modifier = Modifier,
    rate: MutableState<Int> = mutableStateOf(0),
    color: ButtonColor = Themes.Light.ratingStar,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier
    ) {
        for (i in 1..5) {
            Icon(
                modifier = Modifier.size(32.dp)
                    .clickable(
                        enabled = enabled,
                        onClick = {
                            if (rate.value == i) {
                                rate.value = 0
                            } else {
                                rate.value = i
                            }
                        },
                    ),
                painter = if (i <= rate.value) painterResource(Res.drawable.ic_kid_star_filled) else painterResource(Res.drawable.ic_kid_star),
                contentDescription = null,
                tint = if (i <= rate.value) color.hoverBackground!! else color.defaultBackground!!
            )
        }
    }
}