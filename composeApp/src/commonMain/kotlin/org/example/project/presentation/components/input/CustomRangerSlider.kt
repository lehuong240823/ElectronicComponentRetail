package org.example.project.presentation.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import org.example.project.CURRENCY
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomRangerSlider(
    label: String? = null,
    showRange: Boolean = false,
    range: MutableState<ClosedFloatingPointRange<Float>>,
    value: ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    color: ButtonColor = Themes.Light.rangeSliderLayout,
    unit: String = CURRENCY
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!label.isNullOrEmpty()) BodyText(text = label)
            if (showRange) BodyText(text = "${unit}${range.value.start.toInt()}-${range.value.endInclusive.toInt()}")
        }
        RangeSlider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1000f,
            colors = SliderDefaults.colors(
                thumbColor = color.defaultBackground!!,
                activeTrackColor = color.defaultBackground,
                inactiveTrackColor = color.hoverBackground!!
            )
        )
    }
}