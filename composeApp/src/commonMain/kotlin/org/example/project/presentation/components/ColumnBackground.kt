package org.example.project.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.isExpanded
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes

@Composable
fun ColumnBackground(
    rootModifier: Modifier = Modifier,
    rootMaxWidth: MutableState<Int> = remember { mutableStateOf(0) },
    modifier: Modifier = Modifier,
    color: ButtonColor = Themes.Light.secondaryLayout,
    showLoadingOverlay: MutableState<Boolean> = mutableStateOf(false),
    showHeaderAndFooter: Boolean = true,
    showBuyButton: Boolean = false,
    onBuyButtonClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val isExpanded = remember { mutableStateOf(false) }
    var sideMenuMaxWidth by remember { mutableStateOf(0) }
    Box(
        modifier = rootModifier.onGloballyPositioned { coordinates ->
            rootMaxWidth.value = coordinates.size.width
        }
    ) {
        LazyColumn(
            modifier = modifier.background(color = color.defaultBackground!!).fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.spacedBy(Size.Space.S800),
            //contentPadding = PaddingValues(start = sideMenuMaxWidth.dp)
        ) {
            item {
                Header(rootMaxWidth = rootMaxWidth)
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    content()
                }
            }
            item {
                if (rootMaxWidth.value.isExpanded()) {
                    Footer()
                }
            }
        }

        if (showBuyButton) {
            Row(
                modifier = Modifier
                    .shadow(8.dp, shape = RectangleShape)
                    //.padding(horizontal = Size.Space.S600)
                    .align(Alignment.BottomCenter),
            ) {
                Row(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .padding(Size.Space.S200)
                ) {
                    Spacer(Modifier.weight(1f))
                    CustomButton(
                        text = "Buy now",
                        onClick = onBuyButtonClick
                    )
                }

            }
        }

        if (showLoadingOverlay.value) {
            LoadingOverlay()
        }
    }
}

