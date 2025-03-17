package org.example.project.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Themes

@Composable
fun ColumnBackground(
    rootModifier: Modifier = Modifier,
    rootMaxWidth: MutableState<Int> = remember { mutableStateOf(0) },
    modifier: Modifier = Modifier,
    color: ButtonColor = Themes.Light.secondaryLayout,
    showLoadingOverlay: MutableState<Boolean> = mutableStateOf(false),
    content: @Composable () -> Unit
) {
    val isExpanded = remember { mutableStateOf(false) }
    //var rootMaxWidth by remember { mutableStateOf(0) }
    var sideMenuMaxWidth by remember { mutableStateOf(0) }
    Box(
        modifier = rootModifier.onGloballyPositioned { coordinates ->
            rootMaxWidth.value = coordinates.size.width
        }
    ) {
        LazyColumn(
            modifier = modifier.background(color = color.defaultBackground!!),
            //verticalArrangement = Arrangement.spacedBy(Size.Space.S800),
            //contentPadding = PaddingValues(start = sideMenuMaxWidth.dp)
        ) {
            item {
                Header(rootMaxWidth = rootMaxWidth)
            }
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(sideMenuMaxWidth.dp))
                    content()
                }

            }
            item {
                Footer()
            }
        }
        Box(
            modifier = Modifier
                .width(sideMenuMaxWidth.dp)
                .padding(vertical = 100.dp)
        ) {
            SideMenu(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    sideMenuMaxWidth = coordinates.size.width
                },
                isExpanded = isExpanded,
            )
        }
        if (showLoadingOverlay.value) {
            LoadingOverlay()
        }
    }
}

