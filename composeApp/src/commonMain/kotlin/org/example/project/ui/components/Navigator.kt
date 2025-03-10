package org.example.project.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import org.example.project.ui.theme.ButtonColor
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography

@Composable
fun Navigator(
    tabTitles: List<String> = listOf("Tab 1", "Tab 2", "Tab 3"),
    selectedTabIndex: MutableState<Int>,
    color: ButtonColor = Themes.Light.tab
) {
    TabRow(
        selectedTabIndex = selectedTabIndex.value,
        backgroundColor = color.defaultBackground!!
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                text = { BodyText(text = title) },
                selected = selectedTabIndex.value == index,
                selectedContentColor = color.primaryText!!,
                unselectedContentColor = color.secondaryText!!,
                onClick = { selectedTabIndex.value = index }
            )
        }
    }
}