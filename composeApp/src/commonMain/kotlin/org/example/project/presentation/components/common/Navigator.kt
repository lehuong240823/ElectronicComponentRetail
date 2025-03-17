package org.example.project.presentation.components.common

import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Themes

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