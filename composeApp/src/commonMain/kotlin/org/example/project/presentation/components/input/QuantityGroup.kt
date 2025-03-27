package org.example.project.presentation.components.input

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes

@Composable
fun QuantityGroup(
    modifier: Modifier = Modifier,
    quantity: MutableState<Int> = mutableStateOf(1),
    size: Dp = 36.dp,
    color: ButtonColor = Themes.Light.quantityButton,
    onValueChange: (Int) -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomButton(
            modifier = Modifier.size(size),
            color = color,
            shape = RectangleShape,
            contentPadding = PaddingValues(Size.Space.S200),
            text = "-",
            onClick = {
                if (quantity.value > 0) {
                    quantity.value--
                    onValueChange(quantity.value)
                }
            },
        )
        InputField(
            modifier = Modifier.size(size),
            value = quantity.value.toString(),
            onValueChange = {
                if (!it.isNullOrEmpty()) {
                    quantity.value = it.filter {
                        it.isDigit()
                    }.toInt()
                    onValueChange(quantity.value)
                }
            },
            maxWidth = size,
            padding = PaddingValues(Size.Space.S200),
            textAlign = TextAlign.Center,
            shape = RectangleShape
        )
        CustomButton(
            modifier = Modifier.size(size),
            color = color,
            shape = RectangleShape,
            contentPadding = PaddingValues(Size.Space.S200),
            text = "+",
            onClick = {
                quantity.value++
                onValueChange(quantity.value)
            },
        )
    }
}