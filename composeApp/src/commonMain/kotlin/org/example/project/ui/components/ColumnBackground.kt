package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import org.example.project.ui.theme.ButtonColor
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes

@Composable
fun ColumnBackground(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    color: ButtonColor = Themes.Light.secondaryLayout,
    content: LazyListScope.() -> Unit
) {
    val isExpanded = remember { mutableStateOf(false) }
    Column {
        Header()
        Row {
            SideMenu(
                isExpanded = isExpanded,
            )
            LazyColumn(
                modifier = modifier.background(color = color.defaultBackground!!, shape = RectangleShape),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S800)
            ) {
                content()
            }
        }
    }
}